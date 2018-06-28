package com.qi.management.service.write;

import com.qi.management.model.domain.BaseMenu;
import com.sfsctech.core.base.constants.StatusConstants;

/**
 * Class MenuWriteService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface MenuWriteService {

    void save(BaseMenu model);

    void changeStatus(String guid, StatusConstants.Status status);
}
