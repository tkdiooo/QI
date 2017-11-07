package com.qi.bootstrap.util;

import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.inf.IEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class BootstrapUtil
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
public class BootstrapUtil {

    private static String[] OPTIONS_CLASS = {"btn btn-danger", "btn btn-success", "btn btn-info", "btn btn-warning"};

    private static CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);

    /**
     * 匹配下拉列表options
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K, V> List<Map<String, Object>> matchOptions(String cacheKey, IEnum<K, V>... enums) {
        List<Map<String, Object>> options = null;
        if (StringUtil.isNotBlank(cacheKey)) {
            options = (List<Map<String, Object>>) factory.getCacheClient().get(cacheKey);
        }
        if (null != options) {
            return options;
        }
        options = new ArrayList<>();
        for (IEnum<K, V> e : enums) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", e.getCode());
            map.put("text", e.getContent());
            map.put("class", OPTIONS_CLASS[Integer.valueOf(e.getCode().toString())]);
            options.add(map);
        }
        factory.getCacheClient().put(cacheKey, options);
        return options;
    }

}
