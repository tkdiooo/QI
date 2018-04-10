package com.qi.backstage.management.service.write;

import com.qi.backstage.management.model.domain.BaseDatasource;

/**
 * Class DatasourceWriteService
 *
 * @author 张麒 2018-3-7.
 * @version Description:
 */
public interface DatasourceWriteService {

    void save(BaseDatasource model);

}
