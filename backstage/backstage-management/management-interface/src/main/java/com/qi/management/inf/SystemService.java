package com.qi.management.inf;

import com.qi.management.model.dto.SystemDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
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
    RpcResult<SystemDto> getByCode(@RequestBody SystemDto model);

    @RequestMapping("getByGuid")
    RpcResult<SystemDto> getByGuid(@RequestBody SystemDto model);

    @RequestMapping("findAll")
    RpcResult<List<SystemDto>> findAll(@RequestBody SystemDto model);

    @RequestMapping("save")
    RpcResult<SystemDto> save(@RequestBody SystemDto model);

    @RequestMapping("changeStatus")
    void changeStatus(@RequestBody SystemDto model);


}
