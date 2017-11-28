package com.qi.sso.auth.util;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.qi.sso.inf.VerifyService;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.spring.properties.WebsiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil {

    private static final Logger logger = LoggerFactory.getLogger(SingletonUtil.class);

    private static VerifyService verifyService;

    public static VerifyService getVerifyService() {
        if (null == verifyService)
            synchronized (VerifyService.class) {
                if (null == verifyService)
                    verifyService = (VerifyService) SpringContextUtil.getBean(ReferenceConfig.class).get();
            }
        return verifyService;
    }

    private static WebsiteProperties websiteProperties;

    public static WebsiteProperties getWebsiteProperties() {
        if (null == websiteProperties)
            synchronized (WebsiteProperties.class) {
                if (null == websiteProperties)
                    websiteProperties = SpringContextUtil.getBean(WebsiteProperties.class);
            }
        return websiteProperties;
    }
}
