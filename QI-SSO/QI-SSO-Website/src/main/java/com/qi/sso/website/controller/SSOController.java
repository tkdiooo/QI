package com.qi.sso.website.controller;

import com.qi.sso.website.helper.SSOHelper;
import com.qi.sso.website.rpc.consumer.SSOService;
import com.sfsctech.auth.util.JwtCookieUtil;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.result.ValidatorResult;
import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.base.session.SessionInfo;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.ValidatorUtil;
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
    @Autowired
    private SSOProperties properties;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        helper.init(request, response);
        helper.loginBefore(model);
        return "index";
    }

    @PostMapping("login")
    @ResponseBody
    public ActionResult<String> login(HttpServletRequest request, HttpServletResponse response) {
        helper.init(request, response);
        String account = request.getParameter(SSOConstants.LOGIN_ACCOUNT);
        String password = request.getParameter("password");
        ActionResult<String> result = new ActionResult<>();
        // 用户名或密码为空
        if (StringUtil.isBlank(account) || StringUtil.isBlank(password)) {
            result.setSuccess(false);
            result.setMessage(I18NConstants.Tips.LoginAuthNotEmpty);
            return result;
        }
        UserAuthData authData = new UserAuthData(helper.decryptAuthData(account), helper.decryptAuthData(password));
        ValidatorResult valid = ValidatorUtil.validate(authData);
        if (valid.hasErrors()) {
            logger.error("数据校验异常：" + valid.getMessages());
            throw new VerifyException(I18NConstants.Tips.ExceptionValidator, valid);
        }
        authData.setSessionID(request.getSession().getId());
        // 验证登录信息，返回用户对象
        ActionResult<JwtToken> jwtResult = service.login(authData);
        logger.info("用户：" + authData.getAccount() + "登录结果:" + JsonUtil.toJSONString(jwtResult));
        // 登录失败
        if (!jwtResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(I18NConstants.Tips.LoginWrong);
            return result;
        }
        // 登录成功
        String remember = request.getParameter(SSOConstants.LOGIN_REMEMBER);
        // 记住账号
        if (StringUtil.isNotBlank(remember)) {
            // 记录cookie
            helper.getCookieHelper().setCookie(SSOConstants.COOKIE_REMEMBER_LOGIN_ACCOUNT, EncrypterTool.encrypt(EncrypterTool.Security.Aes, authData.getAccount()));
        } else {
            // 删除cookie
            helper.getCookieHelper().clearCookie(SSOConstants.COOKIE_REMEMBER_LOGIN_ACCOUNT);
        }
        // Jwt Cookie
        helper.buildToken(jwtResult.getResult());
        helper.setPortalUrl(result);
        return result;
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SessionInfo sessionInfo = SessionHolder.getSessionInfo();
        logger.info("用户:" + sessionInfo.getUserAuthData().getAccount() + ",注销登录");
        helper.init(request, response);
        logger.info("用户:" + sessionInfo.getUserAuthData().getAccount() + ",销毁token");
        helper.destroyToken();
        JwtToken jt = JwtCookieUtil.getJwtTokenByCookie(helper.getCookieHelper());
        if (null == jt) {
            logger.warn("用户：" + sessionInfo.getUserAuthData().getAccount() + ",注销错误：Cookie信息丢失");
        } else {
            service.logout(jt);
        }
        return "redirect:" + properties.getPortalUrl();
    }
}
