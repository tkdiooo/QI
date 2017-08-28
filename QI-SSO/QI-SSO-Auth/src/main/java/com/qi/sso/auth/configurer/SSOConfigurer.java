package com.qi.sso.auth.configurer;

import com.qi.sso.auth.filter.SSOFilter;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.condition.DistributedAuthentication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Class SSOConfigurer
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
@Configuration
public class SSOConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    @Conditional(DistributedAuthentication.class)
    public FilterRegistrationBean SSOFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SSOFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addInitParameter(SecurityConstants.FILTER_EXCLUDES_KEY, SecurityConstants.SERVER_SUFFIX + "/druid/*");
        return registration;
    }
}
