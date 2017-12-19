package com.qi.backstage.management.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.inf.BaseMenuService;
import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.model.dto.MenuDto;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.common.util.ThrowableUtil;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.rpc.result.ActionResult;
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
public class MenuServiceProvider implements BaseMenuService {

    private final Logger logger = LoggerFactory.getLogger(MenuServiceProvider.class);

    @Autowired
    private MenuReadService readService;

    @Override
    public ActionResult<MenuDto> findBySystem(String system) {
        ActionResult<MenuDto> result = new ActionResult<>();
        try {
            List<MenuDto> list = readService.findBySystem(system, CommonConstants.ROOT_GUID);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage(I18NConstants.Tips.EmptyCollection, "系统编号：" + system + "获取");
                logger.warn(JsonUtil.toJSONString(result.getStatus()));
                logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
            result.setDataSet(list);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result;
    }

}
