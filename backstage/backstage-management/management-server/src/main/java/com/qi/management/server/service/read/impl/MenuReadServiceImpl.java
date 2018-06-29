package com.qi.management.server.service.read.impl;

import com.qi.management.dao.BaseMenuDao;
import com.qi.management.mapper.BaseMenuMapper;
import com.qi.management.model.domain.BaseMenu;
import com.qi.management.model.domain.BaseMenuExample;
import com.qi.management.model.dto.MenuDto;
import com.qi.management.server.service.read.MenuReadService;
import com.sfsctech.support.common.util.StringUtil;
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

    @Autowired
    private BaseMenuDao dao;

    @Override
    public List<BaseMenu> findAll(BaseMenu model) {
        BaseMenuExample example = new BaseMenuExample();
        BaseMenuExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotBlank(model.getParent())) {
            criteria.andParentEqualTo(model.getParent());
        }
        if (StringUtil.isNotBlank(model.getSysguid())) {
            criteria.andSysguidEqualTo(model.getSysguid());
        }
        return mapper.selectByExample(example);
    }

    @Override
    public BaseMenu getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

    @Override
    public List<MenuDto> findBySysCode(String sysCode, String parent) {
        return dao.findBySysCode(sysCode, parent);
    }
}
