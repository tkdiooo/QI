package com.qi.support.resources.filter;

import com.sfsctech.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class SecurityFilter
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
public class SecurityFilter implements Filter {

    public final Logger logger = LoggerFactory.getLogger("logger.filter");

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        String ip = HttpUtil.getRequestIP(request);
        System.out.println(response.getHeader("Access-Control-Allow-Origin"));
        // 跨域请求白名单
        response.setHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
