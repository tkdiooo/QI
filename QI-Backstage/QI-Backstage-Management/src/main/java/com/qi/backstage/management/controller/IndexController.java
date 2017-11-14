package com.qi.backstage.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
@RequestMapping("management")
public class IndexController {

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
