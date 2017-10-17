package com.sfsctech.configurer;

import com.sfsctech.constants.DubboConstants;
import org.springframework.stereotype.Component;

/**
 * Class InitConfig
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
@Component("BACKSTAGE_MODEL_SERIALIZE")
public class KryoSerializeConfig {

    static {
        DubboConstants.addKryoSerializePackage("com.qi.backstage.model.dto");
    }
}
