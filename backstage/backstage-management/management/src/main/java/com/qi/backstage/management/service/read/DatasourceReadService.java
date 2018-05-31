package com.qi.backstage.management.service.read;

import com.qi.backstage.management.model.domain.BaseDatasource;
import com.sfsctech.core.base.domain.model.PagingInfo;

/**
 * Class DatasourceReadService
 *
 * @author 张麒 2018-3-7.
 * @version Description:
 */
public interface DatasourceReadService {

    PagingInfo<BaseDatasource> findByPage(PagingInfo<BaseDatasource> pagingInfo);

    BaseDatasource get(Long id);
}
