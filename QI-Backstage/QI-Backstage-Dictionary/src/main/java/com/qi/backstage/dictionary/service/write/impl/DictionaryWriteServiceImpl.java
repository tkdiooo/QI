package com.qi.backstage.dictionary.service.write.impl;

import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.backstage.mapper.BaseDictionaryMapper;
import com.qi.backstage.model.domain.BaseDictionary;
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
    public void save(BaseDictionary dictionary) {
        dictionary.setGuid(UUIDUtil.base58Uuid());
        dictionary.setStatus(StatusConstants.YesNo.Yes.getCode());
        dictionary.setSort(NumberUtils.INTEGER_ZERO);
        mapper.insert(dictionary);
    }

}
