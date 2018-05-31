package com.qi.platform.backend.rpc.consumer;

import com.qi.backstage.management.inf.MenuService;
import com.qi.backstage.management.inf.SystemService;
import com.qi.backstage.management.model.dto.SystemDto;
import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.core.rpc.result.RpcResult;
import com.sfsctech.support.common.util.ListUtil;
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

    private MenuService menuService;

    private SystemService systemService;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

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
            RpcResult<SystemDto> result = systemService.getByCode(sysCode);
            if (!result.isSuccess()) {
                system = result.getResult();
                factory.getCacheClient().putTimeOut(cache_key, system, CacheConstants.MilliSecond.Minutes30.getContent());
            } else {
                BizException e = new BizException(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                e.setViewName(CommonConstants.VIEW_404);
                throw e;
            }
        }
        return system;
    }
}
