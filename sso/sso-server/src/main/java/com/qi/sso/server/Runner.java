package com.qi.sso.server;

import com.sfsctech.core.auth.sso.config.SSOConfig;
import com.sfsctech.dubbo.starter.annotation.EnableDubboProviderService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableDubboProviderService
@Import(SSOConfig.class)
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
