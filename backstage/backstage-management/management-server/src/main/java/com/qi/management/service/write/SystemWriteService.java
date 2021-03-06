package com.qi.management.service.write;

import com.qi.management.model.domain.BaseSystem;
import com.qi.management.model.dto.SystemDto;
import com.sfsctech.core.base.constants.StatusConstants;

/**
 * Class SystemWriteService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface SystemWriteService {

    void save(BaseSystem entity);

    void changeStatus(SystemDto model);
}
