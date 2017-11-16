package com.qi.backstage.management.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qi.backstage.inf.DictionaryService;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.model.dto.DictionaryDto;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Class DictionaryServiceConsumer
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryServiceConsumer {

    private final Logger logger = LoggerFactory.getLogger(DictionaryServiceConsumer.class);

    private static final String SYSTEM_ADD_OPTIONS = "system_add_options";
    private static final String SYSTEM_ADD_DEFAULT_OPTIONS = "system_add_default_options";

    @Reference
    private DictionaryService service;

    @Autowired
    private CacheFactory factory;

    public List<Map<String, Object>> findChildByNumber(String number) {
        List<Map<String, Object>> options = factory.getList(SYSTEM_ADD_OPTIONS);
        if (null == options) {
            ActionResult<List<DictionaryDto>> result = service.findChildByNumber(number);
            if (result.isSuccess()) {
                options = BootstrapUtil.matchOptions(SYSTEM_ADD_OPTIONS, result.getResult(), "number", "content");
            } else {
                logger.error(JsonUtil.toJSONString(result.getStatus()));
                logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                options = BootstrapUtil.matchOptions(SYSTEM_ADD_DEFAULT_OPTIONS, CommonConstants.SystemType.Default);
            }
        }
        return options;
    }
}
