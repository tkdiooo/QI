package com.qi.management.dao;

import com.qi.management.model.domain.BaseSystemExample;
import com.qi.management.model.dto.SystemDto;
import com.sfsctech.data.mybatis.dao.IBaseDao;

/**
 * Class BaseSystemDao
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
public interface BaseSystemDao extends IBaseDao<SystemDto, Long, BaseSystemExample> {

    SystemDto getByCode(String code);
}
