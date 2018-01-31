package com.qi.backstage.management.service.read;

import com.qi.backstage.management.model.domain.BaseButton;

/**
 * Class ButtonReadService
 *
 * @author 张麒 2018-1-31.
 * @version Description:
 */
public interface ButtonReadService {

    BaseButton getByGuid(String guid);
}
