package com.qi.backstage.management.service.jdbc.impl;

import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.service.jdbc.JdbcService;
import com.sfsctech.database.jdbc.JDBCPool;
import com.sfsctech.database.model.DBConfigModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class JdbcServiceImpl
 *
 * @author 张麒 2018-3-9.
 * @version Description:
 */
@Component
public class JdbcServiceImpl implements JdbcService {

    @Override
    public List<String> showDatabases(BaseDatasource datasource) {
        return JDBCPool.getJdbcTemplate(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), null, datasource.getUsername(), datasource.getPassword())).queryForList("show databases", String.class);
    }

    @Override
    public List<String> showTabls(DBConfigModel config) {
        return JDBCPool.getJdbcTemplate(config).queryForList("show tables", String.class);
    }
}
