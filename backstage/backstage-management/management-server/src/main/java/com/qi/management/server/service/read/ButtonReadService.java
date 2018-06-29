package com.qi.management.server.service.read;

import com.qi.management.model.domain.BaseButton;

import java.util.List;

/**
 * Class ButtonReadService
 *
 * @author 张麒 2018-1-31.
 * @version Description:
 */
public interface ButtonReadService {

    List<BaseButton> findAll(BaseButton model);

    BaseButton getByGuid(String guid);
}
