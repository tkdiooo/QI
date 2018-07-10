package com.qi.dictionary.service.write.impl;

import com.qi.dictionary.mapper.BaseDictionaryMapper;
import com.qi.dictionary.model.domain.BaseDictionary;
import com.qi.dictionary.model.domain.BaseDictionaryExample;
import com.qi.dictionary.constants.CommonConstants;
import com.qi.dictionary.service.write.DictionaryWriteService;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.Cn2SpellUtil;
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
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Override
    public void save(BaseDictionary model) {
        model.setPinyin(Cn2SpellUtil.converterToSpell(model.getContent()) + LabelConstants.COMMA + Cn2SpellUtil.converterToFirstSpell(model.getContent()));
        if (!CommonConstants.ROOT_GUID.equals(model.getParent())) {
            model.setNumber(model.getParent() + model.getNumber());
        }
        if (null == model.getId()) {
            model.setStatus(StatusConstants.Status.Valid.getCode());
            model.setSort(NumberUtils.INTEGER_ZERO);
            mapper.insert(model);
        } else {
            BaseDictionaryExample example = new BaseDictionaryExample();
            example.createCriteria().andIdEqualTo(model.getId());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(BaseDictionary dictionary) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.createCriteria().andNumberEqualTo(dictionary.getNumber());
        mapper.updateByExampleSelective(dictionary, example);
    }

}
