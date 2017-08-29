package com.qi.sso.website.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qi.sso.auth.constants.SSOConstants;
import com.qi.sso.website.helper.SSOHelper;
import com.qi.sso.website.rpc.consumer.SSOService;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.result.ValidatorResult;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.ValidatorUtil;
import com.sfsctech.security.jwt.JwtToken;
import com.sfsctech.security.session.UserAuthData;
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
    private SSOHelper helper;
    @Autowired
    private SSOService service;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        helper.init(request, response);
        helper.loginBefore(model);
        return "index";
    }

    @PostMapping("login")
    @ResponseBody
    public ActionResult<UserAuthData> login(HttpServletRequest request, HttpServletResponse response) {
        helper.init(request, response);
        String account = request.getParameter(SSOConstants.LOGIN_ACCOUNT);
        String password = request.getParameter(SSOConstants.LOGIN_PASSWORD);
        ActionResult<UserAuthData> result = new ActionResult<>();
        // 用户名或密码为空
        if (StringUtil.isBlank(account) || StringUtil.isBlank(password)) {
            result.setSuccess(false);
            result.addMessage(I18NConstants.Tips.LoginAuthNotEmpty);
            return result;
        }
        UserAuthData authData = new UserAuthData(helper.decryptAuthData(account), helper.decryptAuthData(password));
        ValidatorResult valid = ValidatorUtil.validate(authData);
        if (valid.hasErrors()) {
            logger.error("数据校验异常：" + result.getMessages());
            throw new VerifyException(I18NConstants.Tips.ExceptionValidator, valid);
        }
        authData.setSessionID(request.getSession().getId());
        // 验证登录信息，返回用户对象
        ActionResult<JwtToken> actionResult = service.login(authData);
        logger.info("用户：" + authData.getAccount() + "登录结果:" + JSON.toJSONString(actionResult, SerializerFeature.WriteEnumUsingToString));
        // 登录成功
        if (actionResult.isSuccess()) {
            String remember = request.getParameter(SSOConstants.LOGIN_REMEMBER);
            // 记住账号
            if (StringUtil.isNotBlank(remember)) {
                // 记录cookie
                helper.getCookieHelper().setCookie(SSOConstants.COOKIE_REMEMBER_LOGIN_ACCOUNT, EncrypterTool.encrypt(EncrypterTool.Security.Des3, account));
            } else {
                // 删除cookie
                helper.getCookieHelper().clearCookie(SSOConstants.COOKIE_REMEMBER_LOGIN_ACCOUNT);
            }
            helper.buildToken(actionResult.getResult());
            String form_url = request.getParameter(SSOConstants.PARAM_FROM_URL);
            result.addAttach(SSOConstants.PARAM_FROM_URL, form_url);
        }
        result.setSuccess(false);
        result.addMessage(I18NConstants.Tips.LoginWrong);
        return result;
    }
}
