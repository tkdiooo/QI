package com.qi.sso.auth.filter;

import com.qi.sso.auth.util.CacheKeyUtil;
import com.qi.sso.auth.util.SingletonUtil;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.base.session.SessionInfo;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.*;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.dubbox.util.JwtCookieUtil;
import com.sfsctech.dubbox.util.JwtUtil;
import com.sfsctech.rpc.result.ActionResult;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            SessionInfo session = new SessionInfo();
            SessionHolder.setSessionInfo(session);
            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            String requestURI = request.getRequestURI();
            logger.info("Request path：" + requestURI);
            if (ExcludesConstants.isExclusion(requestURI, excludesPattern)) {
                logger.info("Don't need to intercept the path：" + requestURI);
                chain.doFilter(request, response);
                return;
            }
            // SSO用户登录验证
            CookieHelper helper = CookieHelper.getInstance(request, response);
            final JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper);
            if (null != jt) {
                try {
                    ActionResult<JwtToken> result = SingletonUtil.getVerifyService().simpleCheck(jt);
                    // 校验成功
                    if (result.isSuccess()) {
                        String token = EncrypterTool.decrypt(result.getResult().getJwt(), result.getResult().getSalt());
                        Claims claims = JwtUtil.parseJWT(token);
                        // 设置UserAuthData
                        SessionHolder.getSessionInfo().setUserAuthData(CacheKeyUtil.getUserAuthData(claims));
                        // 设置Session attribute
                        SessionHolder.getSessionInfo().setAttribute(CacheKeyUtil.getSessionAttribute(claims));
                        // 设置RoleInfo

                        // 更新token
                        JwtCookieUtil.updateJwtToken(helper, result.getResult());
                        chain.doFilter(request, response);
                        return;
                    } else {
                        logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                    }
                } catch (Exception e) {
                    logger.error(ThrowableUtil.getRootMessage(e));
                }
            }
            if (null != SingletonUtil.getWebsiteProperties().getSession().getRequiredExcludePath() && ExcludesConstants.isExclusion(requestURI, SingletonUtil.getWebsiteProperties().getSession().getRequiredExcludePath())) {
                logger.info("required path：" + requestURI);
                chain.doFilter(request, response);
                return;
            }
            ResponseUtil.setNoCacheHeaders(response);
            // Ajax请求
            if (HttpUtil.isAjaxRequest(request)) {
                response.getWriter().write("<script>window.parent.location.href='" + SingletonUtil.getSSOProperties().getLoginUrl() + "';</script>");
            } else {
                response.sendRedirect(SingletonUtil.getSSOProperties().getLoginUrl());
            }
        } finally {
            SessionHolder.clear();
        }
    }
}
