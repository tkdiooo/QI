package com.qi.management.inf;

import com.qi.management.model.dto.MenuDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class sdsd
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
@RestController
@RequestMapping("menu-service")
@CloudService("management-server")
public interface MenuService {

    @RequestMapping("findBySystemCode")
    RpcResult<List<MenuDto>> findBySystemCode(@RequestBody MenuDto model);

    @RequestMapping("findAll")
    RpcResult<List<MenuDto>> findAll(@RequestBody MenuDto model);

    @RequestMapping("getByGuid")
    RpcResult<MenuDto> getByGuid(@RequestBody MenuDto model);

    @RequestMapping("save")
    RpcResult<MenuDto> save(@RequestBody MenuDto model);

    @RequestMapping("changeStatus")
    void changeStatus(@RequestBody MenuDto model);

    @RequestMapping("sort")
    void sort(@RequestBody MenuDto model);

}
