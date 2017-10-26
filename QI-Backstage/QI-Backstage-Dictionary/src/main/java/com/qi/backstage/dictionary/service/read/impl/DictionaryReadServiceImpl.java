package com.qi.backstage.dictionary.service.read.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.mapper.BaseDictionaryMapper;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.domain.BaseDictionaryExample;
import com.sfsctech.base.model.PagingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryReadServiceImpl
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
@Service
public class DictionaryReadServiceImpl implements DictionaryReadService {

    @Autowired
    private BaseDictionaryMapper mapper;

    @Override
    public List<BaseDictionary> find(BaseDictionary condition) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        BaseDictionaryExample.Criteria criteria = example.createCriteria();
        return mapper.selectByExample(example);
    }

    @Override
    public void findByPage(PagingInfo<BaseDictionary> pagingInfo) {
        PageHelper.startPage(pagingInfo.getPageNum(), pagingInfo.getPageSize());
        PageInfo<BaseDictionary> page = new PageInfo<>(find(pagingInfo.getCondition()));
        pagingInfo.setRecordsTotal(page.getTotal());
        pagingInfo.setData(page.getList());
    }
}
