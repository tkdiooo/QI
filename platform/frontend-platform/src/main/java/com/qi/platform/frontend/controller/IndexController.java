package com.qi.platform.frontend.controller;

import com.sfsctech.authorize.sso.properties.SSOProperties;
import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.constants.SSOConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class IndexController
 *
 * @author 张麒 2017-11-23.
 * @version Description:
 */
@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SSOProperties properties;


    @GetMapping("index")
    public String index(ModelMap model) {
        // title
        model.put("title", "FAMILY ZZL");
        return "index";
    }

    @GetMapping("user")
    public String user(ModelMap model) {
        // 登录页面
        model.put(SSOConstants.LOGING_URL, properties.getLoginUrl());
        // 登出页面
        model.put(SSOConstants.LOGOUT_URL, properties.getLogoutUrl());
        // 用户session信息
        model.put(SSOConstants.CONST_UAMS_ASSERTION, SessionHolder.getSessionInfo().getUserAuthData());
        return "user";
    }
}
