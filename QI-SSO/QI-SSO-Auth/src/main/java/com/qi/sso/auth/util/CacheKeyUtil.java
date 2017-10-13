package com.qi.sso.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.SSOConstants;
import io.jsonwebtoken.Claims;
import org.apache.catalina.User;
import org.apache.catalina.ssi.SSIFsize;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.LinkedHashMap;
import java.util.List;
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
        return HexUtil.getEncryptKey() + SSOConstants.SPLIT_FLAG + CACHE_API_KEY;
    }

    @SuppressWarnings("unchecked")
    public static UserAuthData getUserAuthData(Claims claims) {
        JSONObject jo = new JSONObject();
        jo.putAll((Map) claims.get(SSOConstants.JWT_USER_AUTH_INFO));
        return JSONObject.parseObject(jo.toJSONString(), UserAuthData.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getSessionAttribute(Claims claims){
        return (Map) claims.get(SSOConstants.JWT_SESSION_ATTRIBUTE);
    }
}
