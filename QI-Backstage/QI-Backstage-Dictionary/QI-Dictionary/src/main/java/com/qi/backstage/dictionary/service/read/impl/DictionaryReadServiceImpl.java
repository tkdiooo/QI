package com.qi.backstage.dictionary.service.read.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qi.backstage.dictionary.common.constants.CommonConstants;
import com.qi.backstage.dictionary.mapper.BaseDictionaryMapper;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.model.domain.BaseDictionaryExample;
import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.common.util.StringUtil;
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
    public List<BaseDictionary> findAll(BaseDictionary model) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        BaseDictionaryExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotBlank(model.getParent())) {
            criteria.andParentEqualTo(model.getParent());
        }
        example.setOrderByClause("sort asc");
        return mapper.selectByExample(example);
    }

    @Override
    public BaseDictionary getByNumber(String number) {
        return mapper.selectByNumber(number);
    }

    @Override
    public boolean numberIsExist(BaseDictionary dictionary) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        if (null != dictionary.getId()) {
            BaseDictionary dict = mapper.selectByPrimaryKey(dictionary.getId());
            if (dict.getNumber().equals(dictionary.getNumber())) {
                return true;
            }
        }
        example.createCriteria().andNumberEqualTo(dictionary.getParent().equals(CommonConstants.ROOT_GUID) ? dictionary.getNumber() : (dictionary.getParent() + dictionary.getNumber()));
        return mapper.selectByExample(example).size() == 0;
    }

    //    @Override
    public PagingInfo<DictionaryDto> findByPage(PagingInfo<DictionaryDto> pagingInfo) {
        PageHelper.startPage(pagingInfo.getPageNum(), pagingInfo.getPageSize());
        BaseDictionaryExample example = new BaseDictionaryExample();
        if (ListUtil.isNotEmpty(pagingInfo.getOrder())) {
            example.setOrderByClause(pagingInfo.getOrderByClause());
        }
        PageInfo<BaseDictionary> page = new PageInfo<>(mapper.selectByExample(example));
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(dictionary -> pagingInfo.getData().add(BeanUtil.copyBeanForCglib(dictionary, DictionaryDto.class)));
        return pagingInfo;
    }
}
