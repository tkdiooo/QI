package com.qi.backstage.management.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.inf.MenuService;
import com.qi.backstage.management.model.dto.MenuDto;
import com.qi.backstage.management.service.read.MenuReadService;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.rpc.result.RpcResult;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Class MenuServiceProvider
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Service
public class MenuServiceProvider implements MenuService {

    private final Logger logger = LoggerFactory.getLogger(MenuServiceProvider.class);

    @Autowired
    private MenuReadService readService;

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

}
