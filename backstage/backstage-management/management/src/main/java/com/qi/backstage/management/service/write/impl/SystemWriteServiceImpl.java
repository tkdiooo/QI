package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.mapper.BaseMenuMapper;
import com.qi.backstage.management.model.domain.BaseMenu;
import com.qi.backstage.management.model.domain.BaseMenuExample;
import com.qi.backstage.management.service.write.SystemWriteService;
import com.qi.backstage.management.mapper.BaseSystemMapper;
import com.qi.backstage.management.model.domain.BaseSystem;
import com.qi.backstage.management.model.domain.BaseSystemExample;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.uuid.UUIDUtil;
import com.sfsctech.constants.StatusConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class SystemWriteServiceImpl
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
@Service
public class SystemWriteServiceImpl implements SystemWriteService {

    @Autowired
    private BaseSystemMapper mapper;

    @Autowired
    private BaseMenuMapper menuMapper;

    @Override
    public void save(BaseSystem model) {
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            mapper.insert(model);
        } else {
            BaseSystemExample example = new BaseSystemExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
            // 更新菜单里的系统编号
            BaseMenuExample menuExample = new BaseMenuExample();
            menuExample.createCriteria().andSysguidEqualTo(model.getGuid());
            BaseMenu menu = new BaseMenu();
            menu.setSyscode(model.getCode());
            menuMapper.updateByExampleSelective(menu, menuExample);
        }
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        BaseSystem model = new BaseSystem();
        model.setStatus(status.getCode());
        BaseSystemExample example = new BaseSystemExample();
        example.createCriteria().andGuidEqualTo(guid);
        mapper.updateByExampleSelective(model, example);
    }
}