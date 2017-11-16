package com.qi.backstage.management.service.read.impl;

import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.mapper.BaseSystemMapper;
import com.qi.backstage.model.domain.BaseSystem;
import com.qi.backstage.model.domain.BaseSystemExample;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemReadServiceImpl
 *
 * @author 张麒 2017-11-16.
 * @version Description:
 */
@Service
public class SystemReadServiceImpl implements SystemReadService {

    @Autowired
    private BaseSystemMapper mapper;

    @Override
    public List<BaseSystem> findAll(BaseSystem system) {
        BaseSystemExample example = new BaseSystemExample();
//        BaseSystemExample.Criteria criteria = example.createCriteria();
//        if (StringUtil.isNotBlank(model.getParent())) {
//            criteria.andParentEqualTo(model.getParent());
//        }
//        if (StringUtil.isNotBlank(model.getNumber())) {
//            criteria.andNumberLike(model.getNumber() + LabelConstants.PERCENT);
//            criteria.andNumberNotEqualTo(model.getNumber());
//        }
//        example.setOrderByClause("sort asc");
        return mapper.selectByExample(example);
    }

    @Override
    public BaseSystem getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

}
