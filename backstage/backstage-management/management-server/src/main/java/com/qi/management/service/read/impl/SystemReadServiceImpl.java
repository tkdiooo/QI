package com.qi.management.service.read.impl;

import com.qi.management.dao.BaseSystemDao;
import com.qi.management.mapper.BaseSystemMapper;
import com.qi.management.model.domain.BaseSystem;
import com.qi.management.model.domain.BaseSystemExample;
import com.qi.management.model.dto.SystemDto;
import com.qi.management.service.read.SystemReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemReadServiceImpl
 *
 * @author 张麒 2017-11-16.
 * @version Description:
 */
@Service
public class SystemReadServiceImpl implements SystemReadService {

    @Autowired
    private BaseSystemMapper mapper;

    @Autowired
    private BaseSystemDao dao;

    @Override
    public List<BaseSystem> findAll(BaseSystem system) {
        BaseSystemExample example = new BaseSystemExample();
        return mapper.selectByExample(example);
    }

    @Override
    public BaseSystem getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

    @Override
    public SystemDto getByCode(String code) {
        return dao.getByCode(code);
    }
}
