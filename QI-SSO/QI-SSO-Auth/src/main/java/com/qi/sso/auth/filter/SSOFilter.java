package com.qi.sso.auth.filter;

import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.session.SessionHolder;
import com.sfsctech.security.session.SessionInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
            // SSO用户登录验证
            else {
                chain.doFilter(request, response);
            }
        } finally {
            System.out.println("22323");
            SessionHolder.clear();
        }
    }
}
