package com.qi.backstage.dictionary.service.read.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.mapper.BaseDictionaryMapper;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.domain.BaseDictionaryExample;
import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.ListUtil;
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
    public PagingInfo<DictionaryDto> findByPage(PagingInfo<DictionaryDto> pagingInfo) {
        PageHelper.startPage(pagingInfo.getPageNum(), pagingInfo.getPageSize());
        BaseDictionaryExample example = new BaseDictionaryExample();
        if (ListUtil.isNotEmpty(pagingInfo.getOrder())) {
            example.setOrderByClause(pagingInfo.getOrderByClause());
        }
        PageInfo<BaseDictionary> page = new PageInfo<>(mapper.selectByExample(example));
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(dictionary -> pagingInfo.getData().add(BeanUtil.copyPropertiesNotEmpty(DictionaryDto.class, dictionary)));
        return pagingInfo;
    }
}
