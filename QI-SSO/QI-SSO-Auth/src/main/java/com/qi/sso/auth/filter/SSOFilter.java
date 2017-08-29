package com.qi.sso.auth.filter;

import com.qi.sso.auth.util.UserTokenUtil;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.jwt.JwtToken;
import com.sfsctech.security.session.SessionHolder;
import com.sfsctech.security.session.SessionInfo;
import com.sfsctech.spring.properties.AppConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionHolder.setSessionInfo(new SessionInfo());
        try {
            // 排除请求路径验证
            final HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            boolean bool = SecurityConstants.isExclusion(requestURI);
            logger.info("SSO登录验证：exclusion：[" + bool + "] request uri：[" + requestURI + "] " + getClass());
            if (bool) {
                chain.doFilter(request, response);
            }
            Config config = SpringContextUtil.getBean(Config.class);
            AppConfig appConfig = SpringContextUtil.getBean(AppConfig.class);
            // SSO用户登录验证
            CookieHelper helper = new CookieHelper((HttpServletRequest) request, (HttpServletResponse) response, config);
            final JwtToken jt = UserTokenUtil.getJwtTokenByCookie(helper);
            if (null != jt) {

            } else {
                chain.doFilter(request, response);
            }
        } finally {

            SessionHolder.clear();
        }
    }
}
