package com.qi.platform.backend;

import com.sfsctech.dubbo.sso.config.DubboSSOConfig;
import com.sfsctech.dubbo.starter.annotation.EnableDubboConsumeService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableDubboConsumeService
@Import(DubboSSOConfig.class)
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
