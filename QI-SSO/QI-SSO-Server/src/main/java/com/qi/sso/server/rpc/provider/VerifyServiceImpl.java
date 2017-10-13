package com.qi.sso.server.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.qi.sso.auth.util.CacheKeyUtil;
import com.qi.sso.auth.util.JwtUtil;
import com.qi.sso.inf.VerifyService;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.dubbox.properties.JwtProperties;
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

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private CacheFactory cacheFactory;
    @Autowired
    private JwtProperties jwtConfig;

    @Override
    public ActionResult<JwtToken> check(JwtToken jwtToken) {
        ActionResult<JwtToken> result = new ActionResult<>();

        // 解密salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3, jwtToken.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            result.addMessage("用户校验失败：[" + jwtToken.getSalt_CacheKey() + "] 无法解密出salt_CacheKey。");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        logger.info("用户校验：salt_CacheKey[" + salt_CacheKey + "]。");

        String salt = String.valueOf(cacheFactory.getCacheClient().get(salt_CacheKey));
        if (StringUtil.isBlank(salt)) {
            result.addMessage("用户校验失败 :用户登录超时，已无法找到缓存中的解密salt！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        logger.info("用户校验：salt[" + salt + "]。");

        // 解密Jwt
        String token = EncrypterTool.decrypt(jwtToken.getJwt(), salt);
        if (StringUtil.isBlank(token)) {
            result.addMessage("用户校验失败 :JwtToken无法解密[" + jwtToken.getJwt() + "]。");
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

            // 获取jwt剩余时间
            long loginedTimeStamp = System.currentTimeMillis() - Long.valueOf(claims.get("iat").toString());
            //如果离超时间还有一半左右，重新生成Jwt
            if (jwtConfig.getExpiration() > 0 && loginedTimeStamp >= (jwtConfig.getExpiration() / 2)) {
                token = JwtUtil.generalJwt(claims);
            }
            logger.info("用户：" + authData.getAccount() + " token[" + token + "]。");
            // 重新生成salt以及salt的key
            salt = HexUtil.getEncryptKey();
            logger.info("用户：" + authData.getAccount() + " 加密盐值[" + salt + "]。");
            // 清除salt_CacheKey
            cacheFactory.getCacheClient().remove(salt_CacheKey);
            salt_CacheKey = CacheKeyUtil.getSaltCacheKey();
            logger.info("用户：" + authData.getAccount() + " 生成salt_CacheKey[" + salt_CacheKey + "]。");
            // 缓存salt
            cacheFactory.getCacheClient().putTimeOut(salt_CacheKey, salt, jwtConfig.getExpiration().intValue());
        } catch (Exception e) {
            result.addMessage(e.getMessage());
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA), e);
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        jwtToken.setJwt(token);
        jwtToken.setSalt_CacheKey(EncrypterTool.encrypt(EncrypterTool.Security.Des3, salt_CacheKey));
        result.setResult(jwtToken);
        return result;
    }
}
