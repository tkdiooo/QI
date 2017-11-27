package com.qi.platform.frontend.controller;

import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        // 登录
        model.put(SSOConstants.LOGING_URL, properties.getLoginUrl());
        // title
        model.put("title", "FAMILY ZZL");
        // form_url处理
//        String form_url = request.getParameter(SSOConstants.PARAM_FROM_URL);
//        if (StringUtil.isNotBlank(form_url)) {
//            model.put(SSOConstants.PARAM_FROM_URL, form_url);
//        }
        // 用户Session信息
        if (null != SessionHolder.getSessionInfo().getUserAuthData()) {
            model.put(SSOConstants.CONST_UAMS_ASSERTION, SessionHolder.getSessionInfo().getUserAuthData());
        }
        return "index";
    }
}
