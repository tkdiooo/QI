package com.qi.backstage.management.common.constants;

import com.sfsctech.core.base.enums.BaseEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class CommonConstants
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
public class CommonConstants {

    public enum SystemType implements BaseEnum<String, String> {
        /**
         * 已删除
         */
        Default("0", "默认类型");

        SystemType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        @Override
        public String getCode() {
            return key;
        }

        @Override
        public String getDescription() {
            return value;
        }

        public static String getValueByKey(String key) {
            return BaseEnum.findValue(values(), key);
        }

        public static String getKeyByValue(String value) {
            return BaseEnum.findKey(values(), value);
        }

        private static List<BaseEnum<String, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<BaseEnum<String, String>> getOptions() {
            return options;
        }

        private static Map<String, String> columns = options.stream().collect(Collectors.toMap(BaseEnum::getCode, BaseEnum::getDescription));

        public static Map<String, String> getColumns() {
            return columns;
        }
    }

    public static final String CACHE_SYSTEM_ROOT = "SYSTEM_ROOT";

    public static final String CACHE_SECURITY_ROOT = "SECURITY_ROOT";

    public static final String ROOT_NAME = "Root";

    public static final String ROOT_CLASS = "active";

    public static final String ROOT_GUID = "0000000000000000000000";
}
