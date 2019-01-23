package com.qi.sso.website;

import com.sfsctech.cloud.net.starter.EnableCloudServer;
import com.sfsctech.core.auth.sso.config.SSOConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.csrf.CsrfTokenRepository;
/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@Import(SSOConfig.class)
@EnableWebSecurity
@EnableCloudServer(packages = "com.sfsctech.cloud.base.inf")
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
