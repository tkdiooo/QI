package com.qi.support.resources;

import com.qi.support.resources.filter.SecurityFilter;
import com.sfsctech.constants.LabelConstants;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@SpringBootApplication
public class Runner {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SecurityFilter");
        registration.setOrder(1);
        return registration;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).web(true).run(args);
    }
}
