package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.sso.auth.util.CacheKeyUtil;
import com.qi.sso.inf.LoginService;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.JwtProperties;
import com.sfsctech.dubbox.util.JwtUtil;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Class LoginServiceImpl
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
@Service(retries = -1)
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;

    @Autowired
    private JwtProperties jwtConfig;

    @Override
    public ActionResult<JwtToken> login(UserAuthData authData) {
        ActionResult<JwtToken> result = new ActionResult<>();
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

}
