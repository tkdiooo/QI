package com.qi.sso.auth.properties;

import com.qi.sso.auth.util.JwtUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class AuthProperties
 *
 * @author 张麒 2017-12-4.
 * @version Description:
 */
@Component
@PropertySource("classpath:/auth.properties")
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private Integer expiration;

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        if (null != JwtUtil.config.getExpiration() && null == this.expiration) {
            this.expiration = JwtUtil.config.getExpiration().intValue();
        } else if (null == this.expiration) {
            this.expiration = expiration;
        }
    }
}
