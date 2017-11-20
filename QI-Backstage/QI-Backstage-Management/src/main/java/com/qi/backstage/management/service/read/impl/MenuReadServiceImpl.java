package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.dao.BaseMenuDao;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.mapper.BaseMenuMapper;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseMenuExample;
import com.qi.backstage.model.dto.MenuDto;
import com.sfsctech.common.util.StringUtil;
import com.sun.xml.internal.rngom.parse.host.Base;
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
        if (StringUtil.isNotBlank(model.getGuid())) {
            criteria.andParentEqualTo(model.getGuid());
        } else {
            criteria.andParentEqualTo(CommonConstants.ROOT_GUID);
        }
        return mapper.selectByExample(example);
    }

    @Override
    public BaseMenu getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

    @Override
    public List<MenuDto> findBySystem(String system, String parent) {
        return dao.findBySystem(system, parent);
    }
}
