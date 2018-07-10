package com.qi.sso.server.rpc.provider;

import com.sfsctech.cloud.sso.inf.LoginService;
import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.util.CacheKeyUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.HexUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Class LoginServiceImpl
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Autowired
    private JwtProperties jwtConfig;

    @Override
    public RpcResult<JwtToken> login(@RequestBody UserAuthData authData) {
        RpcResult<JwtToken> result = new RpcResult<>();
        JwtToken jwtToken = new JwtToken();
        // TODO 登录验证

        // 生成Jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(SSOConstants.JWT_USER_AUTH_INFO, authData);
        String jwt = JwtUtil.generalJwt(claims);
        logger.info("用户：" + authData.getAccount() + "，生成的jwt[" + jwt + "]。");
        //生成加密盐值，用于加密JwtToken信息
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + authData.getAccount() + "，生成的加密盐值[" + salt + "]。");
        // 加密JwtToken
        String token = EncrypterTool.encrypt(jwt, salt);
        logger.info("用户：" + authData.getAccount() + "，生成的token[" + token + "]。");

        String salt_CacheKey = CacheKeyUtil.getSaltCacheKey();
        logger.info("用户：" + authData.getAccount() + "，生成的salt_CacheKey[" + salt_CacheKey + "]。");
        // 缓存salt
        factory.getCacheClient().putTimeOut(salt_CacheKey, salt, jwtConfig.getExpiration().intValue());
        // 缓存token
        factory.getCacheClient().putTimeOut(salt_CacheKey + LabelConstants.POUND + salt, token, jwtConfig.getExpiration().intValue());

        jwtToken.setJwt(token);
        jwtToken.setSalt_CacheKey(EncrypterTool.encrypt(EncrypterTool.Security.Des3, salt_CacheKey));
        result.setResult(jwtToken);

        return result;
    }

    @Override
    public void logout(@RequestBody JwtToken jt) {
        // 解密salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3, jt.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            logger.error("用户注销错误：[" + jt.getSalt_CacheKey() + "] 无法解密出salt_CacheKey。");
        }
        logger.info("用户注销：salt_CacheKey[" + salt_CacheKey + "]。");

        String salt = String.valueOf(factory.getCacheClient().get(salt_CacheKey));
        factory.getCacheClient().remove(salt_CacheKey + LabelConstants.POUND + salt);
        logger.info("用户注销：清除JwtToken。");
        factory.getCacheClient().remove(salt);
        logger.info("用户注销：清除加密盐值。");
    }

}
