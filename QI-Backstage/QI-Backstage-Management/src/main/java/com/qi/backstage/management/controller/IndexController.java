package com.qi.backstage.management.controller;

import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.backstage.model.domain.BaseMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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
    private MenuWriteService menuWriteService;

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
