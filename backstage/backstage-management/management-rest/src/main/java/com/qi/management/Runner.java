package com.qi.management;

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.security.config.SecurityConfig;
import com.sfsctech.core.web.config.WebConfig;
import com.sfsctech.data.mybatis.config.MyBatisConfig;
import com.sfsctech.dubbo.sso.config.DubboSSOConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@Import({WebConfig.class, SecurityConfig.class, MyBatisConfig.class, CacheConfig.class, DubboSSOConfig.class, LogbackConfig.class})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
