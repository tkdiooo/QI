package com.qi.backstage.dictionary.rpc.consumer;

import com.qi.backstage.dictionary.inf.DictionaryService;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.core.rpc.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryServiceConsumer
 *
 * @author 张麒 2018-6-1.
 * @version Description:
 */
@Service
public class DictionaryServiceConsumer {

    @Autowired
    private DictionaryReadService readService;

    public ActionResult<List<DictionaryDto>> findChildByNumber(String number) {
        ActionResult<List<DictionaryDto>> result = ActionResult.forSuccess();
        try {
            BaseDictionary model = new BaseDictionary();
            model.setParent(number);
            List<BaseDictionary> list = readService.findAll(model);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("parent：" + number + "获取集合为空");
            }
            result.setResult(BeanUtil.copyListForCglib(list, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
        }
        return result;
    }
}
