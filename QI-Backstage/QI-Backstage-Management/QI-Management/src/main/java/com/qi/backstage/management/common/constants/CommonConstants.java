package com.qi.backstage.management.common.constants;

import com.sfsctech.constants.inf.IEnum;

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

    public enum SystemType implements IEnum<String, String> {
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
        public String getContent() {
            return value;
        }

        public static String getValueByKey(String key) {
            return IEnum.findValue(values(), key);
        }

        public static String getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }

        private static List<IEnum<String, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<IEnum<String, String>> getOptions() {
            return options;
        }

        private static Map<String, String> columns = options.stream().collect(Collectors.toMap(IEnum::getCode, IEnum::getContent));

        public static Map<String, String> getColumns() {
            return columns;
        }
    }

    public static final String CACHE_SYSTEM_ROOT = "SYSTEM_ROOT";

    public static final String ROOT_NAME = "Root";

    public static final String ROOT_CLASS = "active";

    public static final String ROOT_GUID = "0000000000000000000000";
}
