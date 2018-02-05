package com.qi.backstage.management.dao.impl;

import com.qi.backstage.management.dao.BaseMenuDao;
import com.qi.backstage.management.model.domain.BaseMenuExample;
import com.qi.backstage.management.model.dto.MenuDto;
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
@Namespace("com.qi.backstage.management.mapper.BaseMenuMapper")
public class BaseMenuDaoImpl extends BaseDaoImpl<MenuDto, Long, BaseMenuExample> implements BaseMenuDao {

    @Override
    public List<MenuDto> findBySysCode(String sysCode, String parent) {
        Map<String, String> map = new HashMap<>();
        map.put("syscode", sysCode);
        map.put("parent", parent);
        return super.queryForList("findBySystem", map);
    }
}
