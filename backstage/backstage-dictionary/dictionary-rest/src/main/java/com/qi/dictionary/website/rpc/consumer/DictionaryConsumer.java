package com.qi.dictionary.website.rpc.consumer;

import com.qi.dictionary.inf.DictionaryService;
import com.qi.dictionary.model.domain.BaseDictionary;
import com.qi.dictionary.model.dto.DictionaryDto;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
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
public class DictionaryConsumer {

    @Autowired
    private DictionaryService service;

    public List<DictionaryDto> findChildByNumber(String number) {
        RpcResult<List<DictionaryDto>> result = service.findChildByNumber(number);
        return result.getResult();
    }

    public DictionaryDto getByNumber(String number) {
        RpcResult<DictionaryDto> result = service.getByNumber(number);
        return result.getResult();
    }

    public DictionaryDto save(BaseDictionary dictionary) {
        RpcResult<DictionaryDto> result = service.save(BeanUtil.copyBeanForCglib(dictionary, DictionaryDto.class));
        return result.getResult();
    }

    public void changeStatus(String number, StatusConstants.Status status) {
        service.changeStatus(number, status);
    }

    public void sort(String sortable) {
        service.sort(sortable);
    }

    public boolean numberIsExist(BaseDictionary dictionary) {
        RpcResult result = service.numberIsExist(BeanUtil.copyBeanForCglib(dictionary, DictionaryDto.class));
        return result.isSuccess();
    }

}
