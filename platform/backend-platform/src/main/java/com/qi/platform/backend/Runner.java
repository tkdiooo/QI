package com.qi.platform.backend;

import com.sfsctech.cloud.net.starter.EnableCloudController;
import com.sfsctech.core.auth.sso.config.SSOConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@Import(SSOConfig.class)
@EnableCloudController(packages = {"com.qi.*.inf", "com.sfsctech.cloud.base.inf"})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
