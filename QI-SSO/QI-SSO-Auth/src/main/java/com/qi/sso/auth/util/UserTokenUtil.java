package com.qi.sso.auth.util;

import com.qi.sso.common.constants.SSOConstants;
import com.qi.sso.common.token.UserToken;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.cookie.Cookies;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.util.ThrowableUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sfsctech.security.jwt.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class UserTokenUtil
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class UserTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserTokenUtil.class);

    /**
     * 根据Cookie获取UserToken对象
     *
     * @param helper CookieHelper
     * @return
     */
    public static UserToken getUserCookiesToken(CookieHelper helper) {
        String tokenVal = helper.getCookieValue(SSOConstants.COOKIE_USER_TOKEN_NAME);
        String tokenKeyCacheKey = helper.getCookieValue(SSOConstants.COOKIE_USER_TOKEN_KEY_CACHENAME);
        if (StringUtil.isNotBlank(tokenVal) && StringUtil.isNotBlank(tokenKeyCacheKey)) {
            try {
                UserToken userToken = new UserToken();
                userToken.setToken(URLDecoder.decode(tokenVal, SSOConstants.UTF_8));
                userToken.setTokenKey_Cache_Key(URLDecoder.decode(tokenKeyCacheKey, SSOConstants.UTF_8));
                return userToken;
            } catch (UnsupportedEncodingException e) {
                clearUserToken(helper);
                logger.error(ThrowableUtil.getRootMessage(e));
            }
        }
        return null;
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper CookieHelper
     * @param jt     JwtToken
     */
    public static void updateToken(CookieHelper helper, JwtToken jt) {
        updateToken(helper, SSOConstants.COOKIE_USER_TOKEN_NAME, SSOConstants.COOKIE_USER_TOKEN_KEY_CACHENAME, jt);
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper       CookieHelper
     * @param tokenKey     COOKIE_USER_TOKEN_NAME
     * @param tokenSaltKey COOKIE_USER_TOKEN_KEY_CACHENAME
     * @param jt           JwtToken
     */
    public static void updateToken(CookieHelper helper, String tokenKey, String tokenSaltKey, JwtToken jt) {
        try {
            helper.setCookie(tokenKey, URLEncoder.encode(jt.getJwt(), SSOConstants.UTF_8));
            helper.setCookie(tokenSaltKey, URLEncoder.encode(jt.getSalt_CacheKey(), SSOConstants.UTF_8));
            logger.info("set cookies token value [domain:" + helper.getConfig().getDomain() + " Jwt:" + jt.getJwt() + " Salt_CacheKey:" + jt.getSalt_CacheKey() + "]");
        } catch (UnsupportedEncodingException e) {
            clearUserToken(helper);
            logger.error(ThrowableUtil.getRootMessage(e));
        }
    }

    /**
     * 清除UserToken Cookie缓存
     *
     * @param helper CookieHelper
     */
    public static void clearUserToken(CookieHelper helper) {
        helper.clearCookie(SSOConstants.COOKIE_USER_TOKEN_NAME);
        helper.clearCookie(SSOConstants.COOKIE_USER_TOKEN_KEY_CACHENAME);
    }
}
