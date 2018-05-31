package com.qi.backstage.management.common.util;

import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.core.spring.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Class BreadcrumbUtil
 *
 * @author 张麒 2018-1-17.
 * @version Description:
 */
public class BreadcrumbUtil {

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<RedisProxy<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    public static List<Breadcrumb> buildBreadcrumb(BreadcrumbInf inf, String... keys) {
        factory.getCacheClient().remove(keys[0]);
        List<Breadcrumb> list;
        // 获取当前节点缓存数据不为空
        if (null != (list = factory.getList(keys[0]))) {
            return list;
        }
        // 没有上级节点时，默认当前节点为顶级节点
        if (keys.length == 1) {
            list = new ArrayList<>();
            list.add(inf.getBreadcrumb());
            factory.getCacheClient().put(keys[0], list);
            return list;
        }
        // 获取上级节点，如果上级节点为空，则抛出异常
        if (null == (list = factory.getList(keys[1]))) {
            throw new BizException("父级节点缓存数据为空");
        }
        list.add(inf.getBreadcrumb());
        factory.getCacheClient().put(keys[0], list);
        return list;
    }
}
