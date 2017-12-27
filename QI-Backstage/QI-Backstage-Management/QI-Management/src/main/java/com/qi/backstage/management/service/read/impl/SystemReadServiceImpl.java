package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.management.mapper.BaseSystemMapper;
import com.qi.backstage.management.model.domain.BaseSystem;
import com.qi.backstage.management.model.domain.BaseSystemExample;
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

    @Override
    public List<BaseSystem> findAll(BaseSystem system) {
        BaseSystemExample example = new BaseSystemExample();
        return mapper.selectByExample(example);
    }

    @Override
    public BaseSystem getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }
}
