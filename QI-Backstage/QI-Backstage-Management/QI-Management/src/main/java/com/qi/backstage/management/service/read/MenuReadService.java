package com.qi.backstage.management.service.read;

import com.qi.backstage.management.model.domain.BaseMenu;
import com.qi.backstage.management.model.dto.MenuDto;

import java.util.List;

/**
 * Class MenuReadService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface MenuReadService {

    List<BaseMenu> findAll(BaseMenu model);

    BaseMenu getByGuid(String guid);

    List<MenuDto> findBySysCode(String sysCode, String parent);
}
