package com.qi.backstage.management.controller;

import com.qi.backstage.model.domain.BaseSystem;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
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

    @GetMapping("grid")
    public String grid(ModelMap model, BaseSystem system) {
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("options", BootstrapUtil.matchOptions("system_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "system/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        return "system/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        return "system/edit";
    }
}
