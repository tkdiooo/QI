package com.qi.backstage.management.service.jdbc;

import com.qi.backstage.management.model.domain.BaseDatasource;
import com.sfsctech.database.model.DBConfigModel;

import java.util.List;

/**
 * Class JdbcService
 *
 * @author 张麒 2018-3-9.
 * @version Description:
 */
public interface JdbcService {

    List<String> showDatabases(BaseDatasource datasource);

    List<String> showTabls(DBConfigModel config);

}
