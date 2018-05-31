package com.qi.bootstrap.util;

import com.sfsctech.core.base.enums.BaseEnum;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.StringUtil;

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
    public static <K, V> List<Map<String, Object>> matchOptions(String cacheKey, BaseEnum<K, V>... enums) {
        List<Map<String, Object>> options = null;
        if (StringUtil.isNotBlank(cacheKey)) {
            options = (List<Map<String, Object>>) factory.getCacheClient().get(cacheKey);
        }
        if (null != options) {
            return options;
        }
        options = new ArrayList<>();
        for (BaseEnum<K, V> e : enums) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", e.getCode());
            map.put("text", e.getDescription());
            if (StringUtil.isNumeric(e.getCode().toString())) {
                map.put("class", OPTIONS_CLASS[Integer.valueOf(e.getCode().toString())]);
            }
            options.add(map);
        }
        factory.getCacheClient().put(cacheKey, options);
        return options;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<Map<String, Object>> matchOptions(String cacheKey, List<T> list, String valProperty, String textProperty) {
        List<Map<String, Object>> options = null;
        if (StringUtil.isNotBlank(cacheKey)) {
            options = (List<Map<String, Object>>) factory.getCacheClient().get(cacheKey);
        }
        if (null != options) {
            return options;
        }
        options = new ArrayList<>();
        for (T t : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", BeanUtil.getPropertyValue(t, valProperty));
            map.put("text", BeanUtil.getPropertyValue(t, textProperty));
            options.add(map);
        }
        factory.getCacheClient().put(cacheKey, options);
        return options;
    }

}
