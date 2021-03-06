package com.qi.platform.backend.controller;

import com.qi.management.model.dto.MenuDto;
import com.qi.management.model.dto.SystemDto;
import com.qi.platform.backend.rpc.consumer.ManageServiceConsumer;
import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.base.session.SessionHolder;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
public class IndexController {

    @Autowired
    private ManageServiceConsumer manageService;
    @Autowired
    private SSOProperties properties;

    @GetMapping("{code}")
    public String index(@PathVariable(value = "code") String code, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        // 后台首页
        if ("index".equals(code)) {
            return "main";
        }
        // 获取系统信息
        SystemDto system = manageService.findSystemMenuBySystem(code);
        // 设置系统信息
        model.put("system", system);
        // 设置菜单信息
        model.put("menus", system.getChild());
        CookieHelper helper = CookieHelper.getInstance(request, response);
        // 获取重定向URL
        String form_url = helper.getCookieValue(SSOConstants.PARAM_FROM_URL);
        // 重定向URL不等于空，并且不是登录页面URL
        if (StringUtil.isNotBlank(form_url) && !(form_url = EncrypterTool.decrypt(EncrypterTool.Security.Des3CBCHex, form_url)).equals(properties.getLoginUrl())) {
            helper.clearCookie(SSOConstants.PARAM_FROM_URL);
            model.put("url", form_url);
            // 菜单列表选中效果
            for (MenuDto menu : system.getChild()) {
                for (MenuDto child : menu.getChild()) {
                    if (form_url.contains(child.getUrl())) {
                        menu.setChoose(true);
                        child.setChoose(true);
                        break;
                    }
                }
            }
        }
        // 用户session信息
//        model.put(SSOConstants.CONST_UAMS_ASSERTION, SessionHolder.getSessionInfo().getUserAuthData());
        return "index";
    }

    @GetMapping("main")
    public String main() {
        return "main";
    }
}
