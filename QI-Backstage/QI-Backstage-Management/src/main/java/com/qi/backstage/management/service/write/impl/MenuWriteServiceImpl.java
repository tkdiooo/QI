package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.backstage.mapper.BaseMenuMapper;
import com.qi.backstage.model.domain.BaseMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class MenuWriteServiceImpl
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
@Service
public class MenuWriteServiceImpl implements MenuWriteService {

    @Autowired
    private BaseMenuMapper mapper;

    @Override
    public void save(BaseMenu menu) {
        mapper.insert(menu);
    }
}
