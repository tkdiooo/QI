package com.qi.support.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@EnableEurekaServer
@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).web(true).run(args);
    }
}
