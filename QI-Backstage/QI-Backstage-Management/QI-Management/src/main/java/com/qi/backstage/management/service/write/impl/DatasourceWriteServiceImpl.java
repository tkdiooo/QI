package com.qi.backstage.management.service.write.impl;

import com.qi.backstage.management.mapper.BaseDatasourceMapper;
import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.model.domain.BaseDatasourceExample;
import com.qi.backstage.management.service.write.DatasourceWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class DatasourceWriteServiceImpl
 *
 * @author 张麒 2018-3-7.
 * @version Description:
 */
@Service
public class DatasourceWriteServiceImpl implements DatasourceWriteService {

    @Autowired
    private BaseDatasourceMapper mapper;

    @Override
    public void save(BaseDatasource model) {
        if (null == model.getId()) {
            mapper.insert(model);
        } else {
            BaseDatasourceExample example = new BaseDatasourceExample();
            example.createCriteria().andIdEqualTo(model.getId());
            mapper.updateByExampleSelective(model, example);
        }
    }

}
