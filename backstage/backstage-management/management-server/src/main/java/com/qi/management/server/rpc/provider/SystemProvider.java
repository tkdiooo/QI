package com.qi.management.server.rpc.provider;

import com.qi.management.inf.SystemService;
import com.qi.management.model.dto.SystemDto;
import com.qi.management.server.service.read.SystemReadService;
import com.qi.management.server.service.write.SystemWriteService;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemServiceProvider
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@Service
public class SystemProvider implements SystemService {

    private final Logger logger = LoggerFactory.getLogger(SystemProvider.class);

    @Autowired
    private SystemReadService readService;

    @Autowired
    private SystemWriteService writeService;

    @Override
    public RpcResult<SystemDto> getByCode(String code) {
        RpcResult<SystemDto> result = new RpcResult<>();
        try {
            SystemDto dto = readService.getByCode(code);
            if (null == dto) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("系统编号：" + code + "获取集合为空");
                logger.warn(FastJson.toJSONString(result.getStatus()));
                logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
            result.setResult(dto);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result;
    }

    @Override
    public RpcResult<SystemDto> getByGuid(String guid) {
        return new RpcResult<>(BeanUtil.copyBeanForCglib(readService.getByGuid(guid), SystemDto.class));
    }

    @Override
    public RpcResult<List<SystemDto>> findAll(SystemDto model) {
        return new RpcResult<>(BeanUtil.copyListForCglib(readService.findAll(model), SystemDto.class));
    }

    @Override
    public RpcResult<SystemDto> save(SystemDto model) {
        writeService.save(model);
        return new RpcResult<>(BeanUtil.copyBeanForCglib(model, SystemDto.class));
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        writeService.changeStatus(guid, status);
    }
}
