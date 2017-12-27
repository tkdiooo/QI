package com.qi.platform.backend.controller;

import com.qi.backstage.management.model.dto.MenuDto;
import com.qi.platform.backend.rpc.consumer.MenuServiceConsumer;
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

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<MenuDto> list = menuServiceConsumer.findSystemMenuBySystem("backstage.management", "1.0");
        model.put("menus", list);
        return "index";
    }

    @GetMapping("main")
    public String main() {
        return "main";
    }
}
