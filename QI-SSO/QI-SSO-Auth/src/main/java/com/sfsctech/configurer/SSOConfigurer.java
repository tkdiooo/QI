package com.sfsctech.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.qi.sso.auth.filter.SSOFilter;
import com.qi.sso.inf.VerifyService;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.spring.properties.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class SSOConfigurer
 *
 * @author 张麒 2017/10/10.
 * @version Description:
 */
@Configuration
public class SSOConfigurer {

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private SSOProperties properties;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @Bean
    public FilterRegistrationBean SSOFilter() {
        SSOFilter filter = new SSOFilter();
        // Session认证排除路径
        filter.setExcludesPattern(appConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        return registration;
    }

    @Bean
    public ReferenceConfig<VerifyService> referenceConfig() {
        ReferenceConfig<VerifyService> config = new ReferenceConfig<>();
        config.setInterface(VerifyService.class);
        config.setId(VerifyService.class.getSimpleName());
        config.setLazy(properties.getReference().isLazy());
        config.setVersion(properties.getReference().getVersion());
        config.setTimeout(properties.getReference().getTimeout());
        config.setApplication(applicationConfig);
        config.setRegistry(registryConfig);
        return config;
    }
}
