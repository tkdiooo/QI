package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.backstage.mapper.BaseMenuMapper;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseMenuExample;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.uuid.UUIDUtil;
import com.sfsctech.constants.StatusConstants;
import org.apache.commons.lang3.math.NumberUtils;
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
    public void save(BaseMenu model) {
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            model.setSort(NumberUtils.INTEGER_ZERO);
            mapper.insert(model);
        } else {
            BaseMenuExample example = new BaseMenuExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        BaseMenu model = new BaseMenu();
        model.setStatus(status.getCode());
        BaseMenuExample example = new BaseMenuExample();
        example.createCriteria().andGuidEqualTo(guid);
        mapper.updateByExampleSelective(model, example);
    }
}
