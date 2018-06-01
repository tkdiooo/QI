package com.qi.backstage.management.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.management.inf.SystemService;
import com.qi.backstage.management.model.dto.SystemDto;
import com.qi.backstage.management.service.read.SystemReadService;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.rpc.result.RpcResult;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
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
}
