package com.qi.backstage.management.service.write;

import com.qi.backstage.management.model.domain.BaseSystem;
import com.sfsctech.constants.StatusConstants;

/**
 * Class SystemWriteService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface SystemWriteService {

    void save(BaseSystem entity);

    void changeStatus(String guid, StatusConstants.Status status);
}
