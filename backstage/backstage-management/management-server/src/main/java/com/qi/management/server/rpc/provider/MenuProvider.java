package com.qi.management.server.rpc.provider;

import com.qi.management.inf.MenuService;
import com.qi.management.model.dto.MenuDto;
import com.qi.management.server.constants.CommonConstants;
import com.qi.management.server.service.read.MenuReadService;
import com.qi.management.server.service.transactional.MenuTransactionalService;
import com.qi.management.server.service.write.MenuWriteService;
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
 * Class MenuServiceProvider
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Service
public class MenuProvider implements MenuService {

    private final Logger logger = LoggerFactory.getLogger(MenuProvider.class);

    @Autowired
    private MenuReadService readService;

    @Autowired
    private MenuWriteService writeService;

    @Autowired
    private MenuTransactionalService transactionalService;

    @Override
    public RpcResult<List<MenuDto>> findBySystemCode(String sysCode) {
        RpcResult<List<MenuDto>> result = new RpcResult<>();
        try {
            List<MenuDto> list = readService.findBySysCode(sysCode, CommonConstants.ROOT_GUID);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("系统编号：" + sysCode + "获取集合为空");
                logger.warn(FastJson.toJSONString(result.getStatus()));
                logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
            result.setResult(list);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result;
    }

    @Override
    public RpcResult<List<MenuDto>> findAll(MenuDto model) {
        return new RpcResult<>(BeanUtil.copyListForCglib(readService.findAll(model), MenuDto.class));
    }

    @Override
    public RpcResult<MenuDto> getByGuid(String guid) {
        return new RpcResult<>(BeanUtil.copyBeanForCglib(readService.getByGuid(guid), MenuDto.class));
    }

    @Override
    public RpcResult<MenuDto> save(MenuDto model) {
        writeService.save(model);
        return new RpcResult<>(BeanUtil.copyBeanForCglib(model, MenuDto.class));
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        writeService.changeStatus(guid, status);
    }

    @Override
    public void sort(String sortable) {
        transactionalService.sort(sortable);
    }

}
