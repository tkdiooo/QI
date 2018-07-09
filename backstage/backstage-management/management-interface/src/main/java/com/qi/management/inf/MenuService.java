package com.qi.management.inf;

import com.qi.management.model.dto.MenuDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
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
    RpcResult<List<MenuDto>> findBySystemCode(String sysCode);

    @RequestMapping("findAll")
    RpcResult<List<MenuDto>> findAll(MenuDto model);

    @RequestMapping("getByGuid")
    RpcResult<MenuDto> getByGuid(String guid);

    @RequestMapping("save")
    RpcResult<MenuDto> save(MenuDto model);

    @RequestMapping("changeStatus")
    void changeStatus(String guid, StatusConstants.Status status);

    @RequestMapping("sort")
    void sort(String sortable);

}
