package com.qi.backstage.dictionary.service.transactional.impl;

import com.qi.backstage.dictionary.model.domain.BaseDictionaryExample;
import com.qi.backstage.dictionary.service.transactional.DictionaryTransactionalService;
import com.qi.backstage.dictionary.mapper.BaseDictionaryMapper;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.sfsctech.constants.LabelConstants;
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
