package com.qi.management.common.verify.model;

import java.util.List;

/**
 * Class VerifyModel
 *
 * @author 张麒 2018-3-22.
 * @version Description:
 */
public class VerifyModel {

    private Long id;
    private String database;
    private String table;
    private List<ConditionModel> condition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ConditionModel> getCondition() {
        return condition;
    }

    public void setCondition(List<ConditionModel> condition) {
        this.condition = condition;
    }
}
