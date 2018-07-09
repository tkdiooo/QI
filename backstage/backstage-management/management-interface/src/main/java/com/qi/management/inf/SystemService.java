package com.qi.management.inf;

import com.qi.management.model.dto.SystemDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class SystemService
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@RestController
@RequestMapping("system-service")
@CloudService("management-server")
public interface SystemService {

    @RequestMapping("getByCode")
    RpcResult<SystemDto> getByCode(String code);

    @RequestMapping("getByGuid")
    RpcResult<SystemDto> getByGuid(String guid);

    @RequestMapping("findAll")
    RpcResult<List<SystemDto>> findAll(SystemDto model);

    @RequestMapping("save")
    RpcResult<SystemDto> save(SystemDto model);

    @RequestMapping("changeStatus")
    void changeStatus(String guid, StatusConstants.Status status);


}
