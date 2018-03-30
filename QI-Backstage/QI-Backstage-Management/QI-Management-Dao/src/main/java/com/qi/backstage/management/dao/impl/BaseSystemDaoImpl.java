package com.qi.backstage.management.dao.impl;

import com.qi.backstage.management.dao.BaseSystemDao;
import com.qi.backstage.management.model.domain.BaseSystemExample;
import com.qi.backstage.management.model.dto.SystemDto;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.mybatis.annotation.Namespace;
import com.sfsctech.mybatis.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Class BaseSystemDaoImpl
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@Repository
@Namespace("com.qi.backstage.management.mapper.BaseSystemMapper")
public class BaseSystemDaoImpl extends BaseDaoImpl<SystemDto, Long, BaseSystemExample> implements BaseSystemDao {

    @Override
    public SystemDto getByCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("parent", "0000000000000000000000");
        map.put("status", StatusConstants.Status.Valid.getCode().toString());
        return super.queryForObject("getByCode", map);
    }
}
