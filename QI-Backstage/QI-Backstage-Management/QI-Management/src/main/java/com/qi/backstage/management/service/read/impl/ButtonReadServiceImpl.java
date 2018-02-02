package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.management.mapper.BaseButtonMapper;
import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.service.read.ButtonReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class ButtonReadServiceImpl
 *
 * @author 张麒 2018-1-31.
 * @version Description:
 */
@Service
public class ButtonReadServiceImpl implements ButtonReadService {

    @Autowired
    private BaseButtonMapper mapper;

    @Override
    public List<BaseButton> findAll(BaseButton model) {
        return null;
    }

    @Override
    public BaseButton getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }
}
