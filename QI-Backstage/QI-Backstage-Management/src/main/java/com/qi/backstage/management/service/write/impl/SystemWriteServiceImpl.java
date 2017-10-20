package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.service.write.SystemWriteService;
import com.qi.backstage.mapper.BaseSystemMapper;
import com.qi.backstage.model.domain.BaseSystem;
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

    @Override
    public void save(BaseSystem entity) {
        entity.setGuid(UUIDUtil.base58Uuid());
        entity.setStatus(StatusConstants.Status.Valid.getCode());
        mapper.insert(entity);
    }
}
