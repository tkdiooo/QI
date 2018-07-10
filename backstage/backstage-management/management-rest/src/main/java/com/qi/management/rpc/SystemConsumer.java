package com.qi.management.rpc;

import com.qi.management.inf.SystemService;
import com.qi.management.model.domain.BaseSystem;
import com.qi.management.model.dto.SystemDto;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemConsumer
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@Service
public class SystemConsumer {

    @Autowired
    private SystemService systemService;

    public SystemDto getByGuid(String guid) {
        SystemDto dto = new SystemDto();
        dto.setGuid(guid);
        RpcResult<SystemDto> result = systemService.getByGuid(dto);
        return result.getResult();
    }

    public List<SystemDto> findAll(BaseSystem model) {
        RpcResult<List<SystemDto>> result = systemService.findAll(BeanUtil.copyBeanForCglib(model, SystemDto.class));
        return result.getResult();
    }

    public SystemDto save(BaseSystem model) {
        RpcResult<SystemDto> result = systemService.save(BeanUtil.copyBeanForCglib(model, SystemDto.class));
        return result.getResult();
    }

    public void changeStatus(String guid, StatusConstants.Status status) {
        SystemDto dto = new SystemDto();
        dto.setGuid(guid);
        dto.setStatus(status.getCode());
        systemService.changeStatus(dto);
    }

}
