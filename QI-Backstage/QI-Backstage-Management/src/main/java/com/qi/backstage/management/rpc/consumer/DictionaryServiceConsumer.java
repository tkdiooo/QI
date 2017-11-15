package com.qi.backstage.management.rpc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qi.backstage.inf.DictionaryService;
import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryServiceConsumer
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryServiceConsumer {

    private final Logger logger = LoggerFactory.getLogger(DictionaryServiceConsumer.class);

    @Reference
    private DictionaryService service;

    public List<DictionaryDto> findChildByNumber(String number) {
        ActionResult<List<DictionaryDto>> result = service.findChildByNumber(number);
        if (!result.isSuccess()) {
            logger.error(JsonUtil.toJSONString(result.getStatus()));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result.getResult();
    }
}
