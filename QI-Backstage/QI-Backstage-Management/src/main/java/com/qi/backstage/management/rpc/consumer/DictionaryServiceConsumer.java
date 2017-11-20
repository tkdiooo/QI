package com.qi.backstage.management.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qi.backstage.inf.DictionaryService;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.model.dto.DictionaryDto;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.RpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class DictionaryServiceConsumer
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryServiceConsumer {

    private final Logger logger = LoggerFactory.getLogger(DictionaryServiceConsumer.class);

    private static final String SYSTEM_TYPE_OPTIONS = "system_type_options";
    private static final String SYSTEM_ADD_OPTIONS = "system_add_options";
    private static final String SYSTEM_ADD_DEFAULT_OPTIONS = "system_add_default_options";
    private static final String SYSTEM_TYPE_CLOUMNS = "system_type_cloumns";

    @Reference
    private DictionaryService service;

    @Autowired
    private CacheFactory factory;

    public List<DictionaryDto> findChildByNumber(String number) {
        List<DictionaryDto> options = factory.getList(SYSTEM_TYPE_OPTIONS);
        if (null == options) {
            ActionResult<DictionaryDto> result = service.findChildByNumber(number);
            if (RpcUtil.logPrint(result, logger)) {
                options = result.getDataSet();
                factory.getCacheClient().put(SYSTEM_TYPE_OPTIONS, options);
            }
        }
        return options;
    }

    public List<Map<String, Object>> getSystemTypeOptions(String number) {
        List<Map<String, Object>> options = factory.getList(SYSTEM_ADD_OPTIONS);
        if (null == options) {
            List<DictionaryDto> list = findChildByNumber(number);
            if (null != list) {
                options = BootstrapUtil.matchOptions(SYSTEM_ADD_OPTIONS, list, "number", "content");
            } else {
                options = BootstrapUtil.matchOptions(SYSTEM_ADD_DEFAULT_OPTIONS, CommonConstants.SystemType.Default);
            }
        }
        return options;
    }

    public Map<String, String> getSystemTypeCloumns(String number) {
        Map<String, String> cloumns = factory.get(SYSTEM_TYPE_CLOUMNS);
        if (null == cloumns) {
            List<DictionaryDto> list = findChildByNumber(number);
            cloumns = list.stream().collect(Collectors.toMap(DictionaryDto::getNumber, DictionaryDto::getContent));
            factory.getCacheClient().put(SYSTEM_TYPE_CLOUMNS, cloumns);
        }
        return cloumns;
    }
}
