package com.qi.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class Runner extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
