package com.qi.sso.server.rpc.provider;

import com.sfsctech.cloud.base.inf.VerifyService;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.util.CacheKeyUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.auth.sso.util.SSOUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class VerifyServiceImpl
 *
 * @author 张麒 2017/10/12.
 * @version Description:
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    private final Logger logger = LoggerFactory.getLogger(VerifyServiceImpl.class);

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;
    @Autowired
    private JwtProperties jwtConfig;

    @Override
    public RpcResult<JwtToken> simpleVerify(@RequestBody JwtToken jt) {
        RpcResult<JwtToken> result = SSOUtil.generalVerify(jt, logger);
        if (!result.isSuccess()) {
            return result;
        }
        // 解密salt_CacheKey
        String salt_CacheKey = result.getAttachs().get("salt_CacheKey").toString();

        // 会话保持剩余时间（秒）
        long loginedTimeStamp = factory.getCacheClient().ttl(salt_CacheKey + LabelConstants.POUND + jt.getSalt());
        // 如果离超时间还有一半左右，重新生成Jwt
        if (jwtConfig.getExpiration() > 0 && (loginedTimeStamp <= (jwtConfig.getExpiration() / 2))) {
            // 解密Jwt
            String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
            // 获取jwt Claims
            Claims claims = JwtUtil.parseJWT(token);
            // 获取authData
            UserAuthData authData = CacheKeyUtil.getUserAuthData(claims, UserAuthData.class);
            // TODO 处理登录用户权限等功能

            SSOUtil.refreshJwt(claims, authData.getAccount(), salt_CacheKey, jt, logger);
        }
        result.setResult(jt);
        return result;
    }

    @Override
    public RpcResult<JwtToken> complexVerify(@RequestBody JwtToken jt) {
        RpcResult<JwtToken> result = SSOUtil.generalVerify(jt, logger);
        if (!result.isSuccess()) {
            return result;
        }
        // 解密salt_CacheKey
        String salt_CacheKey = result.getAttachs().get("salt_CacheKey").toString();
        // 解密Jwt
        String token = EncrypterTool.decrypt(jt.getJwt(), result.getResult().getSalt());
        if (StringUtil.isBlank(token)) {
            result.setMessage("用户校验失败 :JwtToken无法解密[" + jt.getJwt() + "]。");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }

        try {
            // 获取jwt Claims
            Claims claims = JwtUtil.parseJWT(token);
            // 获取authData
            UserAuthData authData = CacheKeyUtil.getUserAuthData(claims, UserAuthData.class);
            // TODO 处理登录用户权限等功能

            // 获取jwt存在时间(秒)
            long loginedTimeStamp = System.currentTimeMillis() / 1000 - Long.valueOf(claims.get("iat").toString());
            // 如果离超时间还有一半左右，重新生成Jwt
            if (jwtConfig.getExpiration() > 0 && (jwtConfig.getExpiration() / 1000 / 2) <= loginedTimeStamp) {
                SSOUtil.refreshJwt(claims, authData.getAccount(), salt_CacheKey, jt, logger);
            }
            result.setResult(jt);
        } catch (Exception e) {
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA), e);
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        return result;
    }
}
