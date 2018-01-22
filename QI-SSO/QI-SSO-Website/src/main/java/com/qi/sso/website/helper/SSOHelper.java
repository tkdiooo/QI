package com.qi.sso.website.helper;

import com.qi.sso.website.rpc.consumer.SSOService;
import com.sfsctech.auth.util.JwtCookieUtil;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.security.rsa.KeyPairModel;
import com.sfsctech.common.security.rsa.RSA;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.common.util.NumberUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class AuthenticationHelper
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Component
public class SSOHelper {

    private final Logger logger = LoggerFactory.getLogger(SSOHelper.class);

    @Autowired
    private SSOProperties properties;
    @Autowired
    private SSOService service;

    private CookieHelper helper;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.helper = CookieHelper.getInstance(request, response);
        this.request = request;
        this.response = response;
    }

    /**
     * 获取Cookie工具类
     *
     * @return CookieHelper
     */
    public CookieHelper getCookieHelper() {
        return helper;
    }

    /**
     * 构建Token
     *
     * @param jt JwtToken
     */
    public void buildToken(JwtToken jt) {
        JwtCookieUtil.updateJwtToken(helper, jt);
    }

    /**
     * 验证Token
     *
     * @return Boolean
     * @throws Exception
     */
    public boolean checkToken() {
        JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper);
        if (jt == null) {
            logger.warn("do not find cookies token!");
            return false;
        }
        ActionResult<JwtToken> result = service.check(jt);
        if (result.isSuccess()) {
            JwtCookieUtil.updateJwtToken(helper, result.getResult());
            return true;
        }
        return false;
    }

    /**
     * 验证Token
     *
     * @param token_key      token_key
     * @param salt_cache_key salt_cache_key
     * @throws Exception
     */
    public boolean checkToken(String token_key, String salt_cache_key) {
        JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper);
        if (jt == null) {
            logger.warn("do not find cookies token!");
            return false;
        }
        ActionResult<JwtToken> result = service.check(jt);
        if (result.isSuccess()) {
            JwtCookieUtil.updateJwtToken(helper, token_key, salt_cache_key, result.getResult());
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
    public JwtToken getJwtToken() {
        JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper);
        if (jt == null) {
            return null;
        }
        ActionResult<JwtToken> result = service.check(jt);
        return result.getResult();
    }

    /**
     * 销毁Token
     *
     * @throws Exception
     */
    public void destroyToken() {
        JwtCookieUtil.clearJwtToken(helper);
    }

    /**
     * 登录页面准备
     *
     * @param model ModelMap
     */
    public void loginBefore(ModelMap model) {
//        // 注册
//        model.put(SSOConstants.REGISTER_URL, properties.getRegisterUrl());
//        // 找回密码
//        model.put(SSOConstants.FORGET_URL, properties.getForgetUrl());
//        // title
//        model.put("title", "ZZL FAMILY");
        // RSA加密公钥生成
        KeyPairModel keyPair;
        if (null != RSA.LOCAL_KEYPAIRMODEL.get(request.getSession().getId())) {
            keyPair = RSA.LOCAL_KEYPAIRMODEL.get(request.getSession().getId());
        } else {
            keyPair = RSA.getKeys();
            RSA.LOCAL_KEYPAIRMODEL.put(request.getSession().getId(), keyPair);
        }
        String Modulus = keyPair.getPublicKey().getModulus().toString(16);
        String Exponent = keyPair.getPublicKey().getPublicExponent().toString(16);
        model.put("Digits", Modulus.length());
        model.put("Modulus", Modulus);
        model.put("Exponent", Exponent);
        // form_url处理
        String form_url = request.getParameter(SSOConstants.PARAM_FROM_URL);
        if (StringUtil.isBlank(form_url)) {
            form_url = EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, properties.getPortalUrl());
        }
        helper.setCookie(SSOConstants.PARAM_FROM_URL, form_url, NumberUtil.INTEGER_MINUS_ONE);
        // remember_login_account处理
        String account = helper.getCookieValue(SSOConstants.COOKIE_REMEMBER_LOGIN_ACCOUNT);
        if (StringUtil.isNotBlank(account)) {
            model.put(SSOConstants.LOGIN_ACCOUNT, EncrypterTool.decrypt(EncrypterTool.Security.Aes, account));
            model.put(SSOConstants.LOGIN_REMEMBER, "on");
        }
    }

    /**
     * 解密鉴权数据
     *
     * @param result String
     * @return String
     */
    public String decryptAuthData(String result) {
        KeyPairModel keyPair = RSA.LOCAL_KEYPAIRMODEL.get(request.getSession().getId());
        if (null != keyPair) {
            byte[] result_byte = RSA.simpleDecrypt(keyPair.getPrivateKey(), HexUtil.hexStringToBytes(result));
            if (null != result_byte) {
                StringBuilder sb = new StringBuilder(new String(result_byte));
                return sb.reverse().toString();
            }
            RSA.LOCAL_KEYPAIRMODEL.remove(request.getSession().getId());
        }
        return "";
    }

    public void setPortalUrl(ActionResult<String> result) {
        String form_url = helper.getCookieValue(SSOConstants.PARAM_FROM_URL);
        if (StringUtil.isNotBlank(form_url) && !(form_url = EncrypterTool.decrypt(EncrypterTool.Security.Des3ECB, form_url)).equals(properties.getLoginUrl())) {
            helper.clearCookie(SSOConstants.PARAM_FROM_URL);
            if (form_url.contains(SSOConstants.PARAM_FROM_URL)) {
                String[] urls = form_url.split(LabelConstants.BACK_SLASH + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL);
                result.addAttach(SSOConstants.PARAM_FROM_URL, urls[0]);
                helper.setCookie(SSOConstants.PARAM_FROM_URL, EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, urls[1]), NumberUtil.INTEGER_MINUS_ONE);
            } else {
                result.addAttach(SSOConstants.PARAM_FROM_URL, form_url);
            }
        } else {
            result.addAttach(SSOConstants.PARAM_FROM_URL, properties.getPortalUrl());
        }
    }
}
