package com.qi.sso.website.controller;

import com.qi.sso.common.constants.SSOConstants;
import com.sfsctech.spring.properties.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("index")
    public String index(ModelMap model) {
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
        return "index";
    }
}
