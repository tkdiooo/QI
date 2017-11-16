package com.qi.backstage.management.service.read;

import com.qi.backstage.model.domain.BaseSystem;

/**
 * Class SystemReadService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface SystemReadService {

    BaseSystem getByGuid(String guid);
}
