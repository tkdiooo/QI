package com.qi.dictionary.website;

import com.sfsctech.cloud.net.starter.EnableCloudController;
import org.springframework.boot.SpringApplication;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudController(packages = {"com.qi.*.inf", "com.sfsctech.cloud.sso.inf"})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

}
