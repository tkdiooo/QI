package com.qi.sso.server.util;

import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class VerifyUtil {

    private static final Logger logger = LoggerFactory.getLogger(VerifyUtil.class);

    public static void saltCacheKey(ActionResult<JwtToken> result, String salt_CacheKey) {
        result.addMessages("用户校验失败：[" + salt_CacheKey + "] 无法解密出salt_CacheKey。");
        result.setSuccess(false);
        result.setStatus(RpcConstants.Status.Failure);
    }

    public static void salt(ActionResult<JwtToken> result) {
        result.addMessages("用户校验失败 :用户登录超时，已无法找到缓存中的解密salt！");
        result.setSuccess(false);
        result.setStatus(RpcConstants.Status.Failure);
    }
}
