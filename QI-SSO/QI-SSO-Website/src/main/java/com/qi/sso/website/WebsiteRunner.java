package com.qi.sso.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.qi.sso", "com.sfsctech.configurer"})
public class WebsiteRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebsiteRunner.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebsiteRunner.class, args);
    }
}
