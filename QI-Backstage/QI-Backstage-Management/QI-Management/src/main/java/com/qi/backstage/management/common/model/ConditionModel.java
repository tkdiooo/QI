package com.qi.backstage.management.common.model;

/**
 * Class ConditionModel
 *
 * @author 张麒 2018-3-22.
 * @version Description:
 */
public class ConditionModel {

    /* base */
    private String name;
    private String type;
    private boolean notNull;

    /* character */
    private LengthModel length;

    /* number */
    private DigitsModel digits;
    private String min;
    private String max;
    private LengthModel range;

    /* common */
    private String pattern;
    private String constraint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public LengthModel getLength() {
        return length;
    }

    public void setLength(LengthModel length) {
        this.length = length;
    }

    public DigitsModel getDigits() {
        return digits;
    }

    public void setDigits(DigitsModel digits) {
        this.digits = digits;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public LengthModel getRange() {
        return range;
    }

    public void setRange(LengthModel range) {
        this.range = range;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }
}
