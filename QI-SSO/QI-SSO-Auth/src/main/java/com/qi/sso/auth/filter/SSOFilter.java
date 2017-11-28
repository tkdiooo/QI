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
                        String token = EncrypterTool.encrypt(result.getResult().getJwt(), result.getResult().getSalt());
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
            //to login page
            String form_url = HttpUtil.getFullUrl(request);
            // 嵌套form_url处理
//            if (form_url.contains(SSOConstants.PARAM_FROM_URL)) {
//                form_url = form_url.substring(form_url.indexOf(SSOConstants.PARAM_FROM_URL + "="), form_url.length());
//            }
            Boolean ajax = false;
            // 如果是Ajax请求，获取上一步的请求路径
            if (HttpUtil.isAjaxRequest(request)) {
                form_url = request.getHeader("Referer");
                ajax = true;
            }
            PrintWriter out = response.getWriter();
            // 跳转单点登录首页
            SSOProperties properties = SpringContextUtil.getBean(SSOProperties.class);
            final String loginUrl = properties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, form_url);
            ResponseUtil.setNoCacheHeaders(response);
            // Ajax请求
            if (ajax) {

            }
//            else if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
//                Map<String, String> map = new HashMap<>();
//                map.put(SSOConstants.PARAM_FROM_URL, properties.getLoginUrl());
//                map.put("sessionState", SSOConstants.SESSION_TIME_OUT);
//                ResponseUtil.writeJson(map, response);
//            } else if (SSOConstants.IS_IFRAME) {
//                out.write("<script>window.parent.location.href='" + loginUrl + "';</script>");
//            }
            else {
                response.sendRedirect(loginUrl);
            }
        } finally {
            SessionHolder.clear();
        }
    }
}
