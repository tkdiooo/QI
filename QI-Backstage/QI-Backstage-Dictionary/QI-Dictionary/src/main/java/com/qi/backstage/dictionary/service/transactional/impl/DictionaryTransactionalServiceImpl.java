package com.qi.backstage.dictionary.service.transactional.impl;

import com.qi.backstage.dictionary.service.transactional.DictionaryTransactionalService;
import com.qi.backstage.dictionary.mapper.BaseDictionaryMapper;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
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
        for (String sort : sortable.split("#")) {
            model = new BaseDictionary();
            model.setGuid(sort.split(",")[0]);
            model.setSort(Integer.valueOf(sort.split(",")[1]));
            mapper.updateByPrimaryKeySelective(model);
        }
    }
}
