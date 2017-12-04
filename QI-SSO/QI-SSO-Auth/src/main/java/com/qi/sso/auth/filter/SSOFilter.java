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
import java.util.Map;

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
            // SSO用户登录验证
            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            // JwtToken信息
            CookieHelper helper = CookieHelper.getInstance(request, response);
            JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper);
            // 生成SessionInfo
            SessionInfo session = new SessionInfo();
            SessionHolder.setSessionInfo(session);
            // 设置Session attribute
            if (null != jt) {
                Map<String, Object> attribute = SingletonUtil.getCacheFactory().get(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                if (null != attribute) SessionHolder.getSessionInfo().setAttribute(attribute);
            }
            try {
                // 判断请求路径
                String requestURI = request.getRequestURI();
                logger.info("Request path：" + requestURI);
                // 无需校验的路径
                if (ExcludesConstants.isExclusion(requestURI, excludesPattern)) {
                    logger.info("Don't need to intercept the path：" + requestURI);
                    chain.doFilter(request, response);
                    return;
                }
                // Session认证校验
                if (null != jt) {
                    ActionResult<JwtToken> result;
                    if (SingletonUtil.getSSOProperties().getAuth().getWay().equals(SSOConstants.AuthWay.Simple)) {
                        result = SingletonUtil.getVerifyService().simpleVerify(jt);
                    } else {
                        result = SingletonUtil.getVerifyService().complexVerify(jt);
                    }
                    // 校验成功
                    if (result.isSuccess()) {
                        jt = result.getResult();
                        String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
                        Claims claims = JwtUtil.parseJWT(token);
                        // 设置UserAuthData
                        SessionHolder.getSessionInfo().setUserAuthData(CacheKeyUtil.getUserAuthData(claims));
                        // 设置RoleInfo

                        // 更新token
                        JwtCookieUtil.updateJwtToken(helper, jt);
                        chain.doFilter(request, response);
                        return;
                    } else {
                        logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                    }
                }
                // 强制排除的请求路径，无论当前请求路径是否已登录，都通过
                if (null != SingletonUtil.getWebsiteProperties().getSession().getRequiredExcludePath() && ExcludesConstants.isExclusion(requestURI, SingletonUtil.getWebsiteProperties().getSession().getRequiredExcludePath())) {
                    logger.info("required path：" + requestURI);
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                logger.error(ThrowableUtil.getRootMessage(e), e);
            } finally {
                // 更新Session attribute
                if (null != jt && MapUtil.isNotEmpty(SessionHolder.getSessionInfo().getAttribute())) {
                    SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt(), SessionHolder.getSessionInfo().getAttribute(), SingletonUtil.getAuthProperties().getExpiration());
                }
            }
            // 登录超时处理
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
