package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.mapper.BaseMenuMapper;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseMenuExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class MenuReadServiceImpl
 *
 * @author 张麒 2017-11-17.
 * @version Description:
 */
@Service
public class MenuReadServiceImpl implements MenuReadService {

    @Autowired
    private BaseMenuMapper mapper;

    @Override
    public List<BaseMenu> findAll(BaseMenu model) {
        BaseMenuExample example = new BaseMenuExample();
        return mapper.selectByExample(example);
    }

    @Override
    public BaseMenu getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }
}
