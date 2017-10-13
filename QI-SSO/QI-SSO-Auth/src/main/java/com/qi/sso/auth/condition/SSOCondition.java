package com.qi.sso.auth.condition;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.PropertiesConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class SOAExistsCondition
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String authentication = context.getEnvironment().getProperty(PropertiesConstants.WEBSITE_SERVICE_AUTHENTICATION);
        return StringUtil.isNotBlank(authentication) && authentication.equals(CommonConstants.AUTHENTICATION_SSO);
    }
}
