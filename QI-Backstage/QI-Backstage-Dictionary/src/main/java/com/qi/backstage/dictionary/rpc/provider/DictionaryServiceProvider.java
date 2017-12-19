package com.qi.backstage.dictionary.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.inf.DictionaryService;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.common.util.BeanUtil;
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

//    @Override
    public ActionResult<DictionaryDto> getByGuid(String guid) {
        ActionResult<DictionaryDto> result = new ActionResult<>();
        try {
            BaseDictionary model = readService.getByGuid(guid);
            if (null == model) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.ServerError);
                result.setMessage(I18NConstants.Tips.EmptyObject, "GUID：" + guid + "获取");
                logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
            result.setResult(BeanUtil.copyBeanForCglib(result, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result;
    }

    @Override
    public ActionResult<DictionaryDto> findChildByNumber(String number) {
        ActionResult<DictionaryDto> result = new ActionResult<>();
        try {
            BaseDictionary model = new BaseDictionary();
            model.setNumber(number);
            List<BaseDictionary> list = readService.findAll(model);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage(I18NConstants.Tips.EmptyCollection, "parent：" + number + "获取");
                logger.warn(JsonUtil.toJSONString(result.getStatus()));
                logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            }
            result.setDataSet(BeanUtil.copyListForCglib(list, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(JsonUtil.toJSONString(result.getStatus()));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
        }
        return result;
    }
}
