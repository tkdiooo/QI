package com.qi.backstage.dictionary.service.write.impl;

import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.backstage.mapper.BaseDictionaryMapper;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.domain.BaseDictionaryExample;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.uuid.UUIDUtil;
import com.sfsctech.constants.StatusConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class DictionaryWriteServiceImpl
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
@Service
public class DictionaryWriteServiceImpl implements DictionaryWriteService {

    @Autowired
    private BaseDictionaryMapper mapper;

    @Override
    public void save(BaseDictionary model) {
        if (!model.getParent().equals("0000000000000000000000")) {
            mapper.selectByGuid(model.getParent());
            model.setNumber(mapper.selectByGuid(model.getParent()).getNumber() + model.getNumber());
        }
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            model.setSort(NumberUtils.INTEGER_ZERO);
            mapper.insert(model);
        } else {
            BaseDictionaryExample example = new BaseDictionaryExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(String guid, StatusConstants.Status status) {
        BaseDictionary dictionary = new BaseDictionary();
        dictionary.setStatus(status.getCode());
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.createCriteria().andGuidEqualTo(guid);
        mapper.updateByExampleSelective(dictionary, example);
    }

}
