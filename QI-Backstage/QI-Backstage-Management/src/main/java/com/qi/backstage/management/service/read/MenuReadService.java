package com.qi.backstage.management.service.read;

import com.qi.backstage.model.domain.BaseMenu;

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
}
