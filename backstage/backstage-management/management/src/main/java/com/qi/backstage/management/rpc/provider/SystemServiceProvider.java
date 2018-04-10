package com.qi.backstage.management.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.management.inf.SystemService;
import com.qi.backstage.management.model.dto.SystemDto;
import com.qi.backstage.management.service.read.SystemReadService;
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

/**
 * Class SystemServiceProvider
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@Service
public class SystemServiceProvider implements SystemService {

    private final Logger logger = LoggerFactory.getLogger(SystemServiceProvider.class);

    @Autowired
    private SystemReadService readService;

    @Override
    public ActionResult<SystemDto> getByCode(String code) {
        ActionResult<SystemDto> result = new ActionResult<>();
        try {
            SystemDto dto = readService.getByCode(code);
            if (null == dto) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage(I18NConstants.Tips.EmptyObject, "系统编号：" + code + "获取");
                logger.warn(JsonUtil.toJSONString(result.getStatus()));
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
}
