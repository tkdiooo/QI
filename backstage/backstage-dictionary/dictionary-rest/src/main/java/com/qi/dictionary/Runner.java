package com.qi.dictionary;

import com.sfsctech.cloud.net.starter.EnableCloudServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudServer(packages = {"com.qi.*.inf", "com.sfsctech.cloud.base.inf"})
//@EnableHystrix
//@EnableHystrixDashboard
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

}
