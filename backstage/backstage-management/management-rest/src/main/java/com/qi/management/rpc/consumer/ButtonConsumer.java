package com.qi.management.rpc.consumer;

import com.qi.management.inf.ButtonService;
import com.qi.management.model.domain.BaseButton;
import com.qi.management.model.dto.ButtonDto;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class ButtonConsumer
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@Service
public class ButtonConsumer {

    @Autowired
    private ButtonService buttonService;

    public ButtonDto getByGuid(String guid) {
        RpcResult<ButtonDto> result = buttonService.getByGuid(guid);
        return result.getResult();
    }

    public List<ButtonDto> findAll(BaseButton model) {
        RpcResult<List<ButtonDto>> result = buttonService.findAll(BeanUtil.copyBeanForCglib(model, ButtonDto.class));
        return result.getResult();
    }

    public ButtonDto save(BaseButton model) {
        RpcResult<ButtonDto> result = buttonService.save(BeanUtil.copyBeanForCglib(model, ButtonDto.class));
        return result.getResult();
    }

    public void changeStatus(String guid, StatusConstants.Status status) {
        buttonService.changeStatus(guid, status);
    }
}
