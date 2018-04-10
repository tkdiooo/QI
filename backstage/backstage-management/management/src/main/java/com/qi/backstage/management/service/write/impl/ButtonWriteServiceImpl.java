package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.mapper.BaseButtonMapper;
import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.model.domain.BaseButtonExample;
import com.qi.backstage.management.service.write.ButtonWriteService;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.uuid.UUIDUtil;
import com.sfsctech.constants.StatusConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class ButtonWriteServiceImpl
 *
 * @author 张麒 2018-2-1.
 * @version Description:
 */
@Service
public class ButtonWriteServiceImpl implements ButtonWriteService {

    @Autowired
    private BaseButtonMapper mapper;

    @Override
    public void save(BaseButton model) {
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            mapper.insert(model);
        } else {
            BaseButtonExample example = new BaseButtonExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        BaseButton model = new BaseButton();
        model.setStatus(status.getCode());
        BaseButtonExample example = new BaseButtonExample();
        example.createCriteria().andGuidEqualTo(guid);
        mapper.updateByExampleSelective(model, example);
    }
}
