package com.qi.bootstrap.constants;

import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.inf.IEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class BootstrapConstants
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
public class BootstrapConstants {

    /**
     * Class DataStatus
     *
     * @author 张麒 2016年3月28日
     * @version Description：状态枚举
     */
    public enum StatusColumns implements IEnum<Integer, String> {

        Danger(0, "<span class='label label-danger'>" + StatusConstants.Status.getValueByKey(0) + "</span>"),

        Success(1, "<span class='label label-success'>" + StatusConstants.Status.getValueByKey(1) + "</span>"),

        Default(2, "<span class='label label-info'>" + StatusConstants.Status.getValueByKey(2) + "</span>"),

        Warning(3, "<span class='label label-warning'>" + StatusConstants.Status.getValueByKey(3) + "</span>");

        StatusColumns(int key, String value) {
            this.key = key;
            this.value = value;
        }

        private int key;
        private String value;

        @Override
        public Integer getCode() {
            return key;
        }

        @Override
        public String getContent() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }

        private static List<IEnum<Integer, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<IEnum<Integer, String>> getOptions() {
            return options;
        }

        private static Map<Integer, String> columns = options.stream().collect(Collectors.toMap(IEnum::getCode, IEnum::getContent));

        public static Map<Integer, String> getColumns() {
            return columns;
        }
    }
}
