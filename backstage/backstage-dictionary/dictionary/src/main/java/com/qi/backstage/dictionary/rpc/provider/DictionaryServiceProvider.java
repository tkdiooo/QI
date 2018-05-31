package com.qi.backstage.dictionary.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.dictionary.inf.DictionaryService;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.rpc.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Class DictionaryServiceImpl
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryServiceProvider implements DictionaryService {

    private final Logger logger = LoggerFactory.getLogger(DictionaryServiceProvider.class);

    @Autowired
    private DictionaryReadService readService;

    @Override
    public RpcResult<List<DictionaryDto>> findChildByNumber(String number) {
        RpcResult<List<DictionaryDto>> result = new RpcResult<>();
        try {
            BaseDictionary model = new BaseDictionary();
            model.setParent(number);
            List<BaseDictionary> list = readService.findAll(model);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.addMessages("parent：" + number + "获取集合为空");
                logger.warn(result.toString());
            }
            result.setResult(BeanUtil.copyListForCglib(list, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.addMessages(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }
}
