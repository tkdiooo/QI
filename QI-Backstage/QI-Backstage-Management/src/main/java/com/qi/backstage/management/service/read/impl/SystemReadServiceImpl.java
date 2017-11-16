package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.mapper.BaseSystemMapper;
import com.qi.backstage.model.domain.BaseSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public BaseSystem getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

}
