package com.qi.dictionary.util;

import com.qi.dictionary.model.dto.DictionaryDto;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Class DictUtil
 *
 * @author 张麒 2018-2-1.
 * @version Description:
 */
public class DictUtil {

    private static final Logger logger = LoggerFactory.getLogger(DictUtil.class);

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<RedisProxy<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    private static RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);

    public static List<DictionaryDto> findChildByNumber(String number, String cacheKey) {
        List<DictionaryDto> options = factory.getList(cacheKey);
        if (null == options) {
            String responseContent = restTemplate.getForObject("http://www.zzl.com/dictionary/rest/" + number, String.class);
//            ActionResult<DictionaryDto> result = RpcUtil.parseActionResult(responseContent, RpcConstants.Status.Successful, new TypeToken<ActionResult<DictionaryDto>>() {
//            }.getType());
//            if (null != result && RpcUtil.logPrint(result, logger)) {
//                options = result.getDataSet();
//                factory.getCacheClient().putTimeOut(cacheKey, options, CacheConstants.MilliSecond.Minutes30.getContent());
//            }
        }
        return options;
    }
}
