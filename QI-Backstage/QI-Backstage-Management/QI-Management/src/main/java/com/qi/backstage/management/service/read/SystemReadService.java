package com.qi.backstage.management.service.read;

import com.qi.backstage.management.model.domain.BaseSystem;

import java.util.List;

/**
 * Class SystemReadService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface SystemReadService {

    List<BaseSystem> findAll(BaseSystem system);

    BaseSystem getByGuid(String guid);
}
