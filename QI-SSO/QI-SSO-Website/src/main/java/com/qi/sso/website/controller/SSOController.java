package com.qi.sso.website.controller;

import com.qi.sso.common.constants.SSOConstants;
import com.qi.sso.website.authentication.AuthenticationHelper;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.result.ValidatorResult;
import com.sfsctech.common.security.rsa.KeyPairModel;
import com.sfsctech.common.security.rsa.RSA;
import com.sfsctech.common.util.HexUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.ValidatorUtil;
import com.sfsctech.security.session.UserAuthData;
import com.sfsctech.spring.properties.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class SSOController
 *
 * @author 张麒 2017/7/31.
 * @version Description:
 */
@Controller
public class SSOController {

    private final Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthenticationHelper helper;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request) {
        // 注册
        model.put(SSOConstants.REGISTER_URL, appConfig.SSO_REGISTER_URL);
        // 找回密码
        model.put(SSOConstants.FORGET_URL, appConfig.SSO_FORGET_URL);
        // 登录
        model.put(SSOConstants.LOGING_URL, appConfig.SSO_LOGIN_URL);
        // 登出
        model.put(SSOConstants.LOGOUT_URL, appConfig.SSO_LOGOUT_URL);
        // title
        model.put("title", "登录");
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
        return "index";
    }

    @PostMapping("login")
    @ResponseBody
    public ActionResult<UserAuthData> login(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter(SSOConstants.LOGIN_ACCOUNT);
        String password = request.getParameter(SSOConstants.LOGIN_PASSWORD);
        ActionResult<UserAuthData> result = new ActionResult<>();
        // 用户名或密码为空
        if (StringUtil.isBlank(account) || StringUtil.isBlank(password)) {
            result.setSuccess(false);
            result.addMessage(I18NConstants.Tips.LoginAuthNotEmpty);
            return result;
        }
        UserAuthData authData = new UserAuthData(loginDecrypt(request, account), loginDecrypt(request, password));
        ValidatorResult valid = ValidatorUtil.validate(authData);
        if (valid.hasErrors()) {
            logger.error("数据校验异常：" + result.getMessages());
            throw new VerifyException(I18NConstants.Tips.ExceptionValidator, valid);
        }
        String remember = request.getParameter(SSOConstants.LOGIN_REMEMBER);
        String form_url = request.getParameter(SSOConstants.PARAM_FROM_URL);
        // 验证登录信息，返回用户对象
        boolean bool = true;
        helper.createCookieHelper(request, response);
//        result.setSuccess(false);
//        result.addMessage(I18NConstants.Tips.LoginWrong);
        return result;
    }


    public static String loginDecrypt(HttpServletRequest request, String result) {
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
}
