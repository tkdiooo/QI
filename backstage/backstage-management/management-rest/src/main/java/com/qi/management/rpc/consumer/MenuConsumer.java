package com.qi.management.rpc.consumer;

import com.qi.management.inf.MenuService;
import com.qi.management.model.domain.BaseMenu;
import com.qi.management.model.dto.MenuDto;
import com.qi.management.model.dto.MenuDto;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class MenuConsumer
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@Service
public class MenuConsumer {

    @Autowired
    private MenuService menuService;

    public MenuDto getByGuid(String guid) {
        RpcResult<MenuDto> result = menuService.getByGuid(guid);
        return result.getResult();
    }

    public List<MenuDto> findAll(BaseMenu model) {
        RpcResult<List<MenuDto>> result = menuService.findAll(BeanUtil.copyBeanForCglib(model, MenuDto.class));
        return result.getResult();
    }

    public MenuDto save(BaseMenu model) {
        RpcResult<MenuDto> result = menuService.save(BeanUtil.copyBeanForCglib(model, MenuDto.class));
        return result.getResult();
    }

    public void changeStatus(String guid, StatusConstants.Status status) {
        menuService.changeStatus(guid, status);
    }

    public void sort(String sortable) {
        menuService.sort(sortable);
    }
}
