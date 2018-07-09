package com.qi.management.inf;

import com.qi.management.model.dto.DatasourceDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class Datasource
 *
 * @author 张麒 2018-7-9.
 * @version Description:
 */
@RestController
@RequestMapping("datasource-service")
@CloudService("management-server")
public interface DatasourceService {

    @RequestMapping("findByPage")
    RpcResult<PagingInfo<DatasourceDto>> findByPage(PagingInfo<DatasourceDto> pagingInfo);

    @RequestMapping("get")
    RpcResult<DatasourceDto> get(Long id);

    @RequestMapping("save")
    RpcResult<DatasourceDto> save(DatasourceDto model);
}
