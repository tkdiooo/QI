package com.qi.sso.website.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.qi.sso.auth.util.UserTokenUtil;
import com.qi.sso.common.constants.SSOConstants;
import com.qi.sso.common.token.UserToken;
import com.qi.sso.website.rpc.consumer.LoginService;
import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.security.jwt.JwtToken;
import com.sfsctech.security.session.UserAuthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class AuthenticationHelper
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Component
public class AuthenticationHelper {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationHelper.class);

    @Autowired
    private Config config;

    @Autowired
    private LoginService service;

    private CookieHelper helper;

    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.helper = new CookieHelper(request, response, config);
    }

    public void setCookie(String key, String value) {
        helper.setCookie(key, value);
    }

    public void setCookie(String key, String value, int expire) {
        helper.setCookie(key, value, expire);
    }

    public Cookie getCookie(String key) {
        return helper.getCookie(key);
    }

    public String getCookieValue(String key) {
        return helper.getCookieValue(key);
    }

    public void clearCookie(String key) {
        helper.clearCookie(key);
    }

    /**
     * 构建Token
     *
     * @param userAuthData UserAuthData
     */
    public void buildToken(UserAuthData userAuthData) {
        ActionResult<JwtToken> result = service.ssoLogin(userAuthData);
        System.out.println(JSON.toJSONString(result));
        logger.info("用户：" + userAuthData.getAccount() + "登录结果: {status : " + result.getStatus() + ",message:" + result.getMessages() + "}");
        if (result.isSuccess()) {
            UserTokenUtil.updateToken(helper, result.getResult());
        }
    }

    /**
     * 验证Token
     *
     * @return Boolean
     * @throws Exception
     */
    public boolean checkToken() throws Exception {
        UserToken ut = getUserCookiesToken();
        if (ut == null) {
            logger.warn("do not find cookies token!");
            return false;
        }
        UserToken rut = service.ssoCheck(ut);
        if (rut != null && rut.getOperatorResult() == 1) {
            UserTokenUtil.updateToken(helper, rut);
            return true;
        }
        return false;
    }

    /**
     * 验证Token
     *
     * @param tokenKey tokenKey
     * @param token    token
     * @return Boolean
     * @throws Exception
     */
    public boolean checkToken(String tokenKey, String token) throws Exception {
        UserToken ut = getUserCookiesToken();
        if (ut == null) {
            logger.warn("do not find cookies token!");
            return false;
        }
        UserToken rut = service.ssoCheck(ut);
        if (rut != null && rut.getOperatorResult() == 1) {
            UserTokenUtil.updateToken(helper, tokenKey, token, rut);
            return true;
        }
        return false;
    }

    /**
     * 获取UserToken
     *
     * @return UserToken
     * @throws Exception
     */
    public UserToken getTokenInfo() throws Exception {
        UserToken ut = getUserCookiesToken();
        if (ut == null) {
            return null;
        }
        return service.ssoCheck(ut);
    }

    /**
     * 销毁Token
     *
     * @throws Exception
     */
    public void destroyToken() throws Exception {
        UserToken ut = getUserCookiesToken();
        if (ut != null) {
            service.ssoLogout(ut);
        }
        UserTokenUtil.clearUserToken(helper);
    }

    private UserToken getUserCookiesToken() {
        return UserTokenUtil.getUserCookiesToken(helper);
    }

}
