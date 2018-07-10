package com.qi.management.rpc;

import com.qi.management.inf.ButtonService;
import com.qi.management.model.dto.ButtonDto;
import com.qi.management.service.read.ButtonReadService;
import com.qi.management.service.write.ButtonWriteService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Class ButtonProvider
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@Service
public class ButtonProvider implements ButtonService {

    private final Logger logger = LoggerFactory.getLogger(ButtonProvider.class);

    @Autowired
    private ButtonReadService readService;

    @Autowired
    private ButtonWriteService writeService;

    @Override
    public RpcResult<List<ButtonDto>> findAll(@RequestBody ButtonDto model) {
        RpcResult<List<ButtonDto>> result = new RpcResult<>();
        result.setResult(BeanUtil.copyListForCglib(readService.findAll(model), ButtonDto.class));
        return result;
    }

    @Override
    public RpcResult<ButtonDto> getByGuid(@RequestBody ButtonDto model) {
        RpcResult<ButtonDto> result = new RpcResult<>();
        result.setResult(BeanUtil.copyBeanForCglib(readService.getByGuid(model.getGuid()), ButtonDto.class));
        return result;
    }

    @Override
    public RpcResult<ButtonDto> save(ButtonDto model) {
        writeService.save(model);
        return new RpcResult<>(model);
    }

    @Override
    public void changeStatus(@RequestBody ButtonDto model) {
        writeService.changeStatus(model);
    }
}
