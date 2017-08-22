package com.qi.sso.website.authentication;

import com.qi.sso.common.constants.SSOConstants;
import com.qi.sso.common.token.UserToken;
import com.qi.sso.website.rpc.consumer.LoginService;
import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.cookie.CookieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void createCookieHelper(HttpServletRequest request, HttpServletResponse response) {
        this.helper = new CookieHelper(request, response, config);
    }

    public void setCookie(String key, String value) {
        helper.setCookie(key, value);
    }

    public void setCookie(String key, String value, int expire) {
        helper.setCookie(key, value, expire);
    }

    public String getCookie(String key) {
        return helper.getCookie(key);
    }

    public void clearCookie(String key) {
        helper.clearCookie(key);
    }

    /**
     * 构建Token
     *
     * @param account     账号
     * @param accountGuid 账号Guid
     * @param sessionId   Session ID
     * @param sessionData Session Data
     * @throws Exception
     */
    public void buildToken(String account, String accountGuid, String sessionId, String sessionData) throws Exception {
        UserToken ut = new UserToken();
        ut.setAccount(account);
        ut.setUserID(accountGuid);
        ut.setSessionID(sessionId);
        ut.setSessionData(sessionData);
        ut = service.ssoLogin(ut);
        logger.info("用户：" + account + "登录结果 UserToken{" + ut + "}。");
        if (null != ut && ut.getOperatorResult() == 1) {
            UserTokenUtil.updateToken(cookies, ut);
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
        UserToken rut = SSOClientFactory.getSSOClient().ssoCheck(ut);
        if (rut != null && rut.getOperatorResult() == 1) {
            UserTokenUtil.updateToken(cookies, rut);
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
        UserToken rut = SSOClientFactory.getSSOClient().ssoCheck(ut);
        if (rut != null && rut.getOperatorResult() == 1) {
            UserTokenUtil.updateToken(cookies, tokenKey, token, rut);
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
        return SSOClientFactory.getSSOClient().ssoCheck(ut);
    }

    /**
     * 销毁Token
     *
     * @throws Exception
     */
    public void destroyToken() throws Exception {
        UserToken ut = getUserCookiesToken();
        if (ut != null) {
            SSOClientFactory.getSSOClient().ssoLogout(ut);
        }
        UserTokenUtil.clearUserToken(cookies);
    }

    private UserToken getUserCookiesToken() {
        return UserTokenUtil.getUserCookiesToken(cookies);
    }

}
