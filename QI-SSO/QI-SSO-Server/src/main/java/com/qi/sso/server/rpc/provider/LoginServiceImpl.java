package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.qi.sso.inf.LoginService;
import com.sfsctech.auth.constants.AuthConstants;
import com.sfsctech.auth.jwt.JwtConfig;
import com.sfsctech.auth.jwt.JwtToken;
import com.sfsctech.auth.util.CacheUtil;
import com.sfsctech.auth.util.JwtUtil;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.constants.LabelConstants;
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
@Service()
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private CacheFactory cacheFactory;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public ActionResult<JwtToken> login(UserAuthData authData) {
        ActionResult<JwtToken> result = new ActionResult<>();
        JwtToken jwtToken = new JwtToken();
        // 登录验证

        // 生成Jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthConstants.JWT_USER_AUTH_INFO, authData);
        String jwt = JwtUtil.generalJwt(claims);

        //生成加密盐值，用于解密Token信息
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + authData.getAccount() + " 加密盐值[" + salt + "]。");

        String token = EncrypterTool.encrypt(jwt + AuthConstants.SPLIT_FLAG + HexUtil.getHashCode(), salt);
        logger.info("用户：" + authData.getAccount() + " token[" + token + "]。");

        String token_CacheKey = CacheUtil.getTokenCacheKey(authData);
        logger.info("用户：" + authData.getAccount() + " 生成 token_CacheKey[" + token_CacheKey + "]。");
        // 缓存token
        cacheFactory.getCacheClient().putTimeOut(token_CacheKey, token, jwtConfig.getExpiration().intValue());

        String salt_CacheKey = CacheUtil.getSaltCacheKey(authData);
        logger.info("用户：" + authData.getAccount() + " 生成 salt_CacheKey[" + salt_CacheKey + "]。");
        // 缓存salt
        cacheFactory.getCacheClient().putTimeOut(salt_CacheKey, salt, jwtConfig.getExpiration().intValue());

        jwtToken.setJwt(token);
        result.setResult(jwtToken);

        return result;
    }

}
