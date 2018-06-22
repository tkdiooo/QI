package com.qi.backstage.management.inf;

import com.qi.backstage.management.model.dto.SystemDto;
import com.sfsctech.core.base.domain.result.RpcResult;

/**
 * Class SystemService
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
public interface SystemService {

    RpcResult<SystemDto> getByCode(String code);
}
