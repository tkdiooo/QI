package com.qi.management.rpc;

import com.qi.management.inf.DatasourceService;
import com.qi.management.model.domain.BaseDatasource;
import com.qi.management.model.dto.DatasourceDto;
import com.qi.management.service.read.DatasourceReadService;
import com.qi.management.service.write.DatasourceWriteService;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class DatasourceProvider
 *
 * @author 张麒 2018-7-9.
 * @version Description:
 */
@Service
public class DatasourceProvider implements DatasourceService {

    @Autowired
    private DatasourceReadService readService;

    @Autowired
    private DatasourceWriteService writeService;

    @Override
    public RpcResult<PagingInfo<DatasourceDto>> findByPage(@RequestBody PagingInfo<DatasourceDto> pagingInfo) {
        PagingInfo<BaseDatasource> paging = new PagingInfo<>();
        BeanUtil.copyBeanForCglib(pagingInfo, paging);
        paging.setCondition(pagingInfo.getCondition());
        readService.findByPage(paging);
        pagingInfo.setRecordsTotal(paging.getRecordsTotal());
        paging.getData().forEach(data -> pagingInfo.getData().add(BeanUtil.copyBeanForCglib(data, DatasourceDto.class)));
        return new RpcResult<>(pagingInfo);
    }

    @Override
    public RpcResult<DatasourceDto> get(@RequestBody DatasourceDto model) {
        return new RpcResult<>(BeanUtil.copyBeanForCglib(readService.get(model.getId()), DatasourceDto.class));
    }

    @Override
    public RpcResult<DatasourceDto> save(@RequestBody DatasourceDto model) {
        writeService.save(model);
        return new RpcResult<>(BeanUtil.copyBeanForCglib(model, DatasourceDto.class));
    }
}
