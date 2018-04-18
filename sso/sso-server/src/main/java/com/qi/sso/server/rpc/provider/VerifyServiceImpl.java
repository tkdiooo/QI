package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.sso.server.util.VerifyUtil;
import com.sfsctech.authorize.base.inf.VerifyService;
import com.sfsctech.authorize.base.properties.JwtProperties;
import com.sfsctech.authorize.base.util.CacheKeyUtil;
import com.sfsctech.authorize.base.util.JwtUtil;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.util.ThrowableUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.rpc.result.ActionResult;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class VerifyServiceImpl
 *
 * @author 张麒 2017/10/12.
 * @version Description:
 */
@Service(retries = -1)
public class VerifyServiceImpl implements VerifyService {

    private final Logger logger = LoggerFactory.getLogger(VerifyServiceImpl.class);

    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;
    @Autowired
    private JwtProperties jwtConfig;

    @Override
    public ActionResult<JwtToken> simpleVerify(JwtToken jt) {
        ActionResult<JwtToken> result = new ActionResult<>(jt);
        // 解密salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3, jt.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            VerifyUtil.saltCacheKey(result, jt.getSalt_CacheKey());
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        logger.info("用户校验：salt_CacheKey[" + salt_CacheKey + "]。");

        String salt = String.valueOf(factory.getCacheClient().get(salt_CacheKey));
        if (StringUtil.isBlank(salt)) {
            VerifyUtil.salt(result);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        jt.setSalt(salt);
        logger.info("用户校验：salt[" + salt + "]。");

        // 从缓存中获取token信息
        String token = String.valueOf(factory.getCacheClient().get(salt_CacheKey + LabelConstants.POUND + salt));
        if (StringUtil.isBlank(token)) {
            result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的token信息！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }

        if (!token.equals(jt.getJwt())) {
            result.setMessage("用户校验失败 :用户token信息不匹配！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }

        // 会话保持剩余时间（秒）
        long loginedTimeStamp = factory.getCacheClient().ttl(salt_CacheKey + LabelConstants.POUND + salt);
        // 如果离超时间还有一半左右，重新生成Jwt
        if (jwtConfig.getExpiration() > 0 && (loginedTimeStamp <= (jwtConfig.getExpiration() / 2))) {
            // 解密Jwt
            token = EncrypterTool.decrypt(jt.getJwt(), salt);
            // 获取jwt Claims
            Claims claims = JwtUtil.parseJWT(token);
            // 获取authData
            UserAuthData authData = CacheKeyUtil.getUserAuthData(claims);
            // TODO 处理登录用户权限等功能

            this.refreshJwt(claims, authData, salt_CacheKey, jt);
        }
        return result;
    }

    @Override
    public ActionResult<JwtToken> complexVerify(JwtToken jt) {
        ActionResult<JwtToken> result = new ActionResult<>(jt);
        // 解密salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3, jt.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            VerifyUtil.saltCacheKey(result, jt.getSalt_CacheKey());
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        logger.info("用户校验：salt_CacheKey[" + salt_CacheKey + "]。");

        String salt = String.valueOf(factory.getCacheClient().get(salt_CacheKey));
        if (StringUtil.isBlank(salt)) {
            VerifyUtil.salt(result);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        jt.setSalt(salt);
        logger.info("用户校验：salt[" + salt + "]。");

        // 解密Jwt
        String token = EncrypterTool.decrypt(jt.getJwt(), salt);
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
            UserAuthData authData = CacheKeyUtil.getUserAuthData(claims);
            // TODO 处理登录用户权限等功能

            // 获取jwt存在时间(秒)
            long loginedTimeStamp = System.currentTimeMillis() / 1000 - Long.valueOf(claims.get("iat").toString());
            // 如果离超时间还有一半左右，重新生成Jwt
            if (jwtConfig.getExpiration() > 0 && (jwtConfig.getExpiration() / 1000 / 2) <= loginedTimeStamp) {
                this.refreshJwt(claims, authData, salt_CacheKey, jt);
            }
        } catch (Exception e) {
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA), e);
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        return result;
    }

    private void refreshJwt(Claims claims, UserAuthData authData, String salt_CacheKey, JwtToken jwtToken) {
        logger.info("刷新登录用户：" + authData.getAccount() + "的Jwt信息");
        String jwt = JwtUtil.generalJwt(claims);
        logger.info("用户：" + authData.getAccount() + "，生成新的jwt[" + jwt + "]。");
        // 生成新的salt
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + authData.getAccount() + "，生成新的加密盐值[" + salt + "]。");
        // 加密JwtToken
        String token = EncrypterTool.encrypt(jwt, salt);
        logger.info("用户：" + authData.getAccount() + "，生成新的token[" + token + "]。");
        // 清除salt_CacheKey
        factory.getCacheClient().remove(salt_CacheKey);
        // 生成新的salt_CacheKey
        salt_CacheKey = CacheKeyUtil.getSaltCacheKey();
        logger.info("用户：" + authData.getAccount() + "，生成新的salt_CacheKey[" + salt_CacheKey + "]。");
        // 缓存salt
        factory.getCacheClient().putTimeOut(salt_CacheKey, salt, jwtConfig.getExpiration().intValue());
        // 缓存token
        factory.getCacheClient().putTimeOut(salt_CacheKey + LabelConstants.POUND + salt, token, jwtConfig.getExpiration().intValue());
        jwtToken.setJwt(token);
        jwtToken.setSalt(salt);
        jwtToken.setSalt_CacheKey(EncrypterTool.encrypt(EncrypterTool.Security.Des3, salt_CacheKey));
    }
}