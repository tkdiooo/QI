package com.qi.api.basic;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
//@EnableZuulProxy
@SpringBootApplication
//@EnableDiscoveryClient
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).web(WebApplicationType.SERVLET).run(args);
    }
}
