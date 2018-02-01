package com.qi.backstage.management.common.util;

import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.constants.CacheConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class ButtonDictUtil
 *
 * @author 张麒 2018-2-1.
 * @version Description:
 */
public class DictUtil {

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<IRedisService<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    static List<Map<String, Object>> getOptions(String number, String OPTIONS_KEY, String DEFAULT_OPTIONS_KEY, String OPTIONS_DICT_KEY) {
        List<Map<String, Object>> options = factory.getList(OPTIONS_KEY);
        if (null == options) {
            List<DictionaryDto> list = com.qi.backstage.dictionary.util.DictUtil.findChildByNumber(number, OPTIONS_DICT_KEY);
            if (null != list) {
                options = BootstrapUtil.matchOptions(OPTIONS_KEY, list, "number", "content");
            } else {
                options = BootstrapUtil.matchOptions(DEFAULT_OPTIONS_KEY, CommonConstants.SystemType.Default);
            }
        }
        return options;
    }

    static Map<String, String> getTableCloumns(String number, String CLOUMNS_KEY, String OPTIONS_DICT_KEY) {
        Map<String, String> cloumns = factory.get(CLOUMNS_KEY);
        if (null == cloumns) {
            List<DictionaryDto> list = com.qi.backstage.dictionary.util.DictUtil.findChildByNumber(number, OPTIONS_DICT_KEY);
            cloumns = list.stream().collect(Collectors.toMap(DictionaryDto::getNumber, DictionaryDto::getContent));
            factory.getCacheClient().putTimeOut(CLOUMNS_KEY, cloumns, CacheConstants.MilliSecond.Minutes30.getContent());
        }
        return cloumns;
    }

    public static class Button {

        private static final String BUTTON_TYPE_DICT = "button_type_dict";
        private static final String BUTTON_ADD_OPTIONS = "button_add_options";
        private static final String BUTTON_ADD_DEFAULT_OPTIONS = "button_add_default_options";
        private static final String BUTTON_TYPE_TABLE = "button_type_table";

        private static final String DICT_NUMNER_BUTTON_TYPE = "00016";

        public static List<Map<String, Object>> options() {
            return DictUtil.getOptions(DICT_NUMNER_BUTTON_TYPE, BUTTON_ADD_OPTIONS, BUTTON_ADD_DEFAULT_OPTIONS, BUTTON_TYPE_DICT);
        }

        public static Map<String, String> cloumns() {
            return DictUtil.getTableCloumns(DICT_NUMNER_BUTTON_TYPE, BUTTON_TYPE_TABLE, BUTTON_TYPE_DICT);
        }
    }

    public static class System {

        private static final String SYSTEM_TYPE_DICT = "system_type_dict";
        private static final String SYSTEM_ADD_OPTIONS = "system_add_options";
        private static final String SYSTEM_ADD_DEFAULT_OPTIONS = "system_add_default_options";
        private static final String SYSTEM_TYPE_TABLE = "system_type_table";

        private static final String DICT_NUMNER_SYSTEM_TYPE = "00015";

        public static List<Map<String, Object>> options() {
            return DictUtil.getOptions(DICT_NUMNER_SYSTEM_TYPE, SYSTEM_ADD_OPTIONS, SYSTEM_ADD_DEFAULT_OPTIONS, SYSTEM_TYPE_DICT);
        }

        public static Map<String, String> cloumns() {
            return DictUtil.getTableCloumns(DICT_NUMNER_SYSTEM_TYPE, SYSTEM_TYPE_TABLE, SYSTEM_TYPE_DICT);
        }
    }

}
