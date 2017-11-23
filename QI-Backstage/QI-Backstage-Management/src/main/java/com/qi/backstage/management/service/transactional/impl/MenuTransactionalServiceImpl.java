package com.qi.backstage.management.service.transactional.impl;

import com.qi.backstage.management.service.transactional.MenuTransactionalService;
import com.qi.backstage.mapper.BaseMenuMapper;
import com.qi.backstage.model.domain.BaseMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class MenuTransactionalServiceImpl
 *
 * @author 张麒 2017-11-23.
 * @version Description:
 */
@Service
@Transactional
public class MenuTransactionalServiceImpl implements MenuTransactionalService {

    @Autowired
    private BaseMenuMapper mapper;

    @Override
    public void sort(String sortable) {
        BaseMenu model;
        for (String sort : sortable.split("#")) {
            model = new BaseMenu();
            model.setGuid(sort.split(",")[0]);
            model.setSort(Integer.valueOf(sort.split(",")[1]));
            mapper.updateByPrimaryKeySelective(model);
        }
    }
}
