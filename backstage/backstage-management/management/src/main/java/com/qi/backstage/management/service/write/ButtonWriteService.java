package com.qi.backstage.management.service.write;

import com.qi.backstage.management.model.domain.BaseButton;
import com.sfsctech.constants.StatusConstants;

/**
 * Class ButtonWriteService
 *
 * @author 张麒 2018-2-1.
 * @version Description:
 */
public interface ButtonWriteService {

    void save(BaseButton model);

    void changeStatus(String guid, StatusConstants.Status status);
}
