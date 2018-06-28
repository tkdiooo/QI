package com.qi.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
public class IndexController {

    @GetMapping("system")
    public String system(ModelMap model) {
        model.put("path", "system/index");
        return "index";
    }

    @GetMapping("security")
    public String security(ModelMap model) {
        model.put("path", "security/index");
        return "index";
    }
}
