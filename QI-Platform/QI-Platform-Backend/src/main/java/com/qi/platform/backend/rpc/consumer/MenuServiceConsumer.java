package com.qi.platform.backend.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qi.backstage.inf.BaseMenuService;
import com.qi.backstage.model.dto.DictionaryDto;
import com.qi.backstage.model.dto.MenuDto;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.RpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class MenuServiceConsumer
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Service
public class MenuServiceConsumer {

    private final Logger logger = LoggerFactory.getLogger(MenuServiceConsumer.class);

    @Reference
    private BaseMenuService service;

    @Autowired
    private CacheFactory factory;

    public List<MenuDto> findSystemMenuBySystem(String system, String version) {
        String cache_key = system + LabelConstants.POUND + version;
        factory.getCacheClient().remove(cache_key);
        List<MenuDto> list = factory.getList(cache_key);
        if (null == list) {
            ActionResult<MenuDto> result = service.findBySystem(system);
            if (RpcUtil.logPrint(result, logger)) {
                list = result.getDataSet();
                factory.getCacheClient().put(cache_key, list);
            }
        }
        return list;
    }
}
