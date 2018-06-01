package com.qi.sso.server;

import com.sfsctech.core.auth.sso.config.SSOConfig;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.dubbo.starter.annotation.EnableDubboConsumeService;
import com.sfsctech.dubbo.starter.annotation.EnableDubboProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableDubboProviderService
@EnableDubboConsumeService
@Import(SSOConfig.class)
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
