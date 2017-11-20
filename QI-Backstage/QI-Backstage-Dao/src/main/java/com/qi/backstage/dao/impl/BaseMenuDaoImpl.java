package com.qi.backstage.dao.impl;

import com.qi.backstage.dao.BaseMenuDao;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseMenuExample;
import com.qi.backstage.model.dto.MenuDto;
import com.sfsctech.mybatis.annotation.Namespace;
import com.sfsctech.mybatis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class BaseMenuDaoImpl
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Repository
@Namespace("com.qi.backstage.mapper.BaseMenuMapper")
public class BaseMenuDaoImpl extends BaseDaoImpl<MenuDto, Long, BaseMenuExample> implements BaseMenuDao {

    @Override
    public List<MenuDto> findBySystem(String system, String parent) {
        Map<String, String> map = new HashMap<>();
        map.put("system", system);
        map.put("parent", parent);
        return super.queryForList("findBySystem", map);
    }
}
