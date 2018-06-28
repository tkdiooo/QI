package com.qi.management.inf;

import com.qi.management.model.dto.MenuDto;
import com.sfsctech.core.base.domain.result.RpcResult;

import java.util.List;

/**
 * Class sdsd
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
public interface MenuService {

    RpcResult<List<MenuDto>> findBySystemCode(String sysCode);

}
