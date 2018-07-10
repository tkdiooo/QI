package com.qi.dictionary.rpc;

import com.qi.dictionary.inf.DictionaryService;
import com.qi.dictionary.model.domain.BaseDictionary;
import com.qi.dictionary.model.dto.DictionaryDto;
import com.qi.dictionary.service.read.DictionaryReadService;
import com.qi.dictionary.service.transactional.DictionaryTransactionalService;
import com.qi.dictionary.service.write.DictionaryWriteService;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public RpcResult<List<DictionaryDto>> findChildByNumber(@RequestBody DictionaryDto dto) {
        RpcResult<List<DictionaryDto>> result = new RpcResult<>();
        try {
            List<BaseDictionary> list = readService.findByParent(dto.getNumber());
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("parent：" + dto.getNumber() + "获取字典集合为空");
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
    public RpcResult<DictionaryDto> getByNumber(@RequestBody DictionaryDto dto) {
        RpcResult<DictionaryDto> result = new RpcResult<>();
        try {
            BaseDictionary model = readService.getByNumber(dto.getNumber());
            if (null == model) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("number：" + dto.getNumber() + "获取字典对象为空");
                logger.warn(result.toString());
            } else {
                result.setResult(BeanUtil.copyBeanForCglib(model, DictionaryDto.class));
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public RpcResult<DictionaryDto> save(@RequestBody DictionaryDto dictionary) {
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
    public void changeStatus(@RequestBody DictionaryDto dictionary) {
        writeService.changeStatus(dictionary);
    }

    @Override
    public void sort(@RequestBody DictionaryDto dto) {
        transactionalService.sort(dto.getSortable());
    }

    @Override
    public RpcResult numberIsExist(@RequestBody DictionaryDto dictionary) {
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
