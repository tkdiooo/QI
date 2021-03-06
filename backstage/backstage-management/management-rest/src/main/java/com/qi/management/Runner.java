package com.qi.management;

import com.sfsctech.cloud.net.starter.EnableCloudServer;
import org.springframework.boot.SpringApplication;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudServer(packages = {"com.qi.*.inf", "com.sfsctech.cloud.base.inf"})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
