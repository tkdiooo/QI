package com.qi.backstage.management.rpc.consumer;

import com.google.common.reflect.TypeToken;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.constants.CacheConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.RpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;

    @Autowired
    private RestTemplate restTemplate;

    public List<DictionaryDto> findChildByNumber(String number) {
        List<DictionaryDto> options = factory.getList(SYSTEM_TYPE_OPTIONS);
        if (null == options) {
            String responseContent = restTemplate.getForObject("http://www.zzl.com/dictionary/rest/" + number, String.class);
            ActionResult<DictionaryDto> result = RpcUtil.parseActionResult(responseContent, RpcConstants.Status.Successful, new TypeToken<ActionResult<DictionaryDto>>() {
            }.getType());
            if (null != result && RpcUtil.logPrint(result, logger)) {
                options = result.getDataSet();
                factory.getCacheClient().putTimeOut(SYSTEM_TYPE_OPTIONS, options, CacheConstants.MilliSecond.Minutes30.getContent());
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
            factory.getCacheClient().putTimeOut(SYSTEM_TYPE_CLOUMNS, cloumns, CacheConstants.MilliSecond.Minutes30.getContent());
        }
        return cloumns;
    }
}
