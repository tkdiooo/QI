package com.qi.backstage.management.dao;

import com.qi.backstage.management.model.domain.BaseMenuExample;
import com.qi.backstage.management.model.dto.MenuDto;
import com.sfsctech.mybatis.dao.IBaseDao;

import java.util.List;

/**
 * Class BaseMenuDao
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
public interface BaseMenuDao extends IBaseDao<MenuDto, Long, BaseMenuExample> {

    List<MenuDto> findBySysCode(String sysCode, String parent);

}
