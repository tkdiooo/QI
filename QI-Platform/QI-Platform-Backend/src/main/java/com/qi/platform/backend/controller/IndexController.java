package com.qi.platform.backend.controller;

import com.qi.backstage.management.model.dto.MenuDto;
import com.qi.platform.backend.rpc.consumer.MenuServiceConsumer;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
public class IndexController {

    @Autowired
    private MenuServiceConsumer menuServiceConsumer;
    @Autowired
    private SSOProperties properties;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<MenuDto> list = menuServiceConsumer.findSystemMenuBySystem("backstage.management", "1.0");
        model.put("menus", list);

        CookieHelper helper = CookieHelper.getInstance(request, response);
        String form_url = helper.getCookieValue(SSOConstants.PARAM_FROM_URL);
        if (StringUtil.isNotBlank(form_url) && !(form_url = EncrypterTool.decrypt(EncrypterTool.Security.Des3ECB, form_url)).equals(properties.getLoginUrl())) {
            helper.clearCookie(SSOConstants.PARAM_FROM_URL);
            model.put("url", form_url);
            for (MenuDto menu : list) {
                for (MenuDto child : menu.getChild()) {
                    if (form_url.contains(child.getUrl())) {
                        menu.setChoose(true);
                        child.setChoose(true);
                        break;
                    }
                }
            }
        }

        return "index";
    }

    @GetMapping("main")
    public String main() {
        return "main";
    }
}
