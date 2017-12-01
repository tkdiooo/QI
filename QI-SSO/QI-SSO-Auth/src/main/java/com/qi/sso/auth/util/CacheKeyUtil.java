package com.qi.sso.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SSOConstants;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * Class CacheUtil
 *
 * @author 张麒 2017/10/10.
 * @version Description:
 */
public class CacheKeyUtil {

    private static final String CACHE_API_KEY = "%!##@$%|$#$%(^)$}$*{^*+%";

    /**
     * 据用户鉴权数据生成salt缓存的key
     */
    public static String getSaltCacheKey() {
        return HexUtil.getEncryptKey() + LabelConstants.DOUBLE_POUND + CACHE_API_KEY;
    }

    /**
     * 获取用户鉴权数据
     *
     * @param claims Jwt Claims
     * @return UserAuthData
     */
    @SuppressWarnings("unchecked")
    public static UserAuthData getUserAuthData(Claims claims) {
        JSONObject jo = new JSONObject();
        jo.putAll((Map) claims.get(SSOConstants.JWT_USER_AUTH_INFO));
        return JSONObject.parseObject(jo.toJSONString(), UserAuthData.class);
    }

    /**
     * 获取用户权限数据
     *
     * @param claims Jwt Claims
     * @return UserAuthData
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getUserPermitData(Claims claims) {
        return (Map) claims.get(SSOConstants.JWT_PERMIT_ATTRIBUTE);
    }
}
