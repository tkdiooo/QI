package com.qi.management.rpc;

import com.qi.management.inf.DatasourceService;
import com.qi.management.model.domain.BaseDatasource;
import com.qi.management.model.dto.DatasourceDto;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DatasourceConsumer
 *
 * @author 张麒 2018-7-9.
 * @version Description:
 */
@Service
public class DatasourceConsumer {

    @Autowired
    private DatasourceService service;

    public PagingInfo<BaseDatasource> findByPage(PagingInfo<BaseDatasource> pagingInfo) {
        RpcResult<PagingInfo<DatasourceDto>> result = service.findByPage(new PagingInfo<>(pagingInfo, BeanUtil.copyBeanForCglib(pagingInfo.getCondition(), DatasourceDto.class)));
        List<BaseDatasource> list = new ArrayList<>();
        result.getResult().getData().forEach(data -> list.add(BeanUtil.copyBeanForCglib(data, BaseDatasource.class)));
        return new PagingInfo<>(result.getResult(), list);
    }

    public BaseDatasource get(Long id) {
        DatasourceDto dto = new DatasourceDto();
        dto.setId(id);
        RpcResult<DatasourceDto> result = service.get(dto);
        return BeanUtil.copyBeanForCglib(result.getResult(), BaseDatasource.class);
    }

    public DatasourceDto save(BaseDatasource model) {
        RpcResult<DatasourceDto> result = service.save(BeanUtil.copyBeanForCglib(model, DatasourceDto.class));
        return result.getResult();
    }

}
