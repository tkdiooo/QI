package com.qi.dictionary.server.rpc.provider;

import com.qi.dictionary.inf.DictionaryService;
import com.qi.dictionary.model.domain.BaseDictionary;
import com.qi.dictionary.model.dto.DictionaryDto;
import com.qi.dictionary.server.service.read.DictionaryReadService;
import com.qi.dictionary.server.service.transactional.DictionaryTransactionalService;
import com.qi.dictionary.server.service.write.DictionaryWriteService;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryServiceImpl
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryProvider implements DictionaryService {

    private final Logger logger = LoggerFactory.getLogger(DictionaryProvider.class);

    @Autowired
    private DictionaryReadService readService;

    @Autowired
    private DictionaryWriteService writeService;

    @Autowired
    private DictionaryTransactionalService transactionalService;

    @Override
    public RpcResult<List<DictionaryDto>> findChildByNumber(String parent) {
        RpcResult<List<DictionaryDto>> result = new RpcResult<>();
        try {
            List<BaseDictionary> list = readService.findByParent(parent);
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("parent：" + parent + "获取字典集合为空");
                logger.warn(result.toString());
            }
            result.setResult(BeanUtil.copyListForCglib(list, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public RpcResult<DictionaryDto> getByNumber(String number) {
        RpcResult<DictionaryDto> result = new RpcResult<>();
        try {
            BaseDictionary model = readService.getByNumber(number);
            if (null != model) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("number：" + number + "获取字典对象为空");
                logger.warn(result.toString());
            }
            result.setResult(BeanUtil.copyBeanForCglib(model, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public RpcResult<DictionaryDto> save(DictionaryDto dictionary) {
        RpcResult<DictionaryDto> result = new RpcResult<>();
        try {
            writeService.save(dictionary);
            result.setResult(dictionary);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public void changeStatus(String number, StatusConstants.Status status) {
        writeService.changeStatus(number, status);
    }

    @Override
    public void sort(String sortable) {
        transactionalService.sort(sortable);
    }

    @Override
    public RpcResult numberIsExist(DictionaryDto dictionary) {
        RpcResult result = new RpcResult();
        try {
            result.setSuccess(readService.numberIsExist(dictionary));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

}
