package com.qi.backstage.management.service.read.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qi.backstage.management.mapper.BaseDatasourceMapper;
import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.model.domain.BaseDatasourceExample;
import com.qi.backstage.management.service.read.DatasourceReadService;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class DatasourceReadServiceImpl
 *
 * @author 张麒 2018-3-7.
 * @version Description:
 */
@Service
public class DatasourceReadServiceImpl implements DatasourceReadService {

    @Autowired
    private BaseDatasourceMapper mapper;

    @Override
    public PagingInfo<BaseDatasource> findByPage(PagingInfo<BaseDatasource> pagingInfo) {
        PageHelper.startPage(pagingInfo.getPageNum(), pagingInfo.getPageSize());
        BaseDatasourceExample example = new BaseDatasourceExample();
        if (ListUtil.isNotEmpty(pagingInfo.getOrder())) {
            example.setOrderByClause(pagingInfo.getOrderByClause());
        }
        PageInfo<BaseDatasource> page = new PageInfo<>(mapper.selectByExample(example));
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(dictionary -> pagingInfo.getData().add(BeanUtil.copyBeanForCglib(dictionary, BaseDatasource.class)));
        return pagingInfo;
    }

    @Override
    public BaseDatasource get(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

}
