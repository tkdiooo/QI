package com.qi.dictionary.service.transactional.impl;

import com.qi.dictionary.mapper.BaseDictionaryMapper;
import com.qi.dictionary.model.domain.BaseDictionary;
import com.qi.dictionary.model.domain.BaseDictionaryExample;
import com.qi.dictionary.service.transactional.DictionaryTransactionalService;
import com.sfsctech.core.base.constants.LabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class DictionaryTransactionalServiceImpl
 *
 * @author 张麒 2017-11-9.
 * @version Description:
 */
@Service
@Transactional
public class DictionaryTransactionalServiceImpl implements DictionaryTransactionalService {

    @Autowired
    private BaseDictionaryMapper mapper;

    @Override
    public void sort(String sortable) {
        BaseDictionary model;
        BaseDictionaryExample example;
        for (String sort : sortable.split(LabelConstants.POUND)) {
            String[] sp = sort.split(LabelConstants.COMMA);
            model = new BaseDictionary();
            model.setSort(Integer.valueOf(sp[1]));
            example = new BaseDictionaryExample();
            example.createCriteria().andNumberEqualTo(sp[0]);
            mapper.updateByExampleSelective(model, example);
        }
    }
}
