package com.qi.backstage.management.inf;

import com.qi.backstage.management.model.dto.MenuDto;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class sdsd
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
public interface BaseMenuService {

    ActionResult<MenuDto> findBySystem(String sysGuid);

}
