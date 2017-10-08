package com.qi.sso.website;

import com.sfsctech.dubbox.properties.DubboConfig;
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
public class Runner {

    public static void main(String[] args) {
        DubboConfig.setServicePackage("com.qi.sso.website.rpc");
        SpringApplication.run(Runner.class, args);
    }
}
