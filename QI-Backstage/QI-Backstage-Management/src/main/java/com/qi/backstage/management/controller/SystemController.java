package com.qi.backstage.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class SystemController
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "system/edit";
    }
}
