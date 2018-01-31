package com.qi.backstage.dictionary.service.write.impl;

import com.qi.backstage.dictionary.common.constants.CommonConstants;
import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.backstage.dictionary.mapper.BaseDictionaryMapper;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.model.domain.BaseDictionaryExample;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
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
    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;

    @Override
    public void save(BaseDictionary model) {
        if (!model.getParent().equals(CommonConstants.ROOT_GUID)) {
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
        factory.getCacheClient().remove(model.getGuid());
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
