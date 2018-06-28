package com.qi.management.dao;

import com.qi.management.model.domain.BaseMenuExample;
import com.qi.management.model.dto.MenuDto;
import com.sfsctech.data.mybatis.dao.IBaseDao;

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
