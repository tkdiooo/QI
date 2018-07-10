package com.qi.management.inf;

import com.qi.management.model.dto.ButtonDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class ButtonService
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@RestController
@RequestMapping("button-service")
@CloudService("management-server")
public interface ButtonService {

    @RequestMapping("findAll")
    RpcResult<List<ButtonDto>> findAll(@RequestBody ButtonDto model);

    @RequestMapping("getByGuid")
    RpcResult<ButtonDto> getByGuid(@RequestBody ButtonDto model);

    @RequestMapping("save")
    RpcResult<ButtonDto> save(@RequestBody ButtonDto model);

    @RequestMapping("changeStatus")
    void changeStatus(@RequestBody ButtonDto model);
}
