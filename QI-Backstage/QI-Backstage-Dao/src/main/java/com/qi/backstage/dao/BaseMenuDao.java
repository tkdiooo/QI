package com.qi.backstage.dao;

import com.qi.backstage.model.domain.BaseMenuExample;
import com.qi.backstage.model.dto.MenuDto;
import com.sfsctech.mybatis.dao.IBaseDao;

import java.util.List;

/**
 * Class BaseMenuDao
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
public interface BaseMenuDao extends IBaseDao<MenuDto, Long, BaseMenuExample> {

    List<MenuDto> findBySystem(String system, String parent);

}
