package com.qi.sso.server;

import com.sfsctech.cloud.client.starter.EnableCloudClient;
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
@EnableCloudClient
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
