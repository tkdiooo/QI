package com.qi.platform.backend.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qi.backstage.management.inf.MenuService;
import com.qi.backstage.management.inf.SystemService;
import com.qi.backstage.management.model.dto.SystemDto;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.CacheConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.RpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class MenuServiceConsumer
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Service
public class ManageServiceConsumer {

    private final Logger logger = LoggerFactory.getLogger(ManageServiceConsumer.class);

    @Reference
    private MenuService menuService;

    @Reference
    private SystemService systemService;

    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;

    /**
     * 根据系统编号获取系统菜单
     *
     * @param sysCode
     * @return
     */
    public SystemDto findSystemMenuBySystem(String sysCode) {
        String cache_key = sysCode + LabelConstants.DOUBLE_POUND;
        factory.getCacheClient().remove(cache_key);
        SystemDto system = factory.get(cache_key);
        if (null == system) {
            ActionResult<SystemDto> result = systemService.getByCode(sysCode);
            if (RpcUtil.logPrint(result, logger)) {
                system = result.getResult();
                factory.getCacheClient().putTimeOut(cache_key, system, CacheConstants.MilliSecond.Minutes30.getContent());
            } else {
                throw new BizException(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
        }
        return system;
    }
}
