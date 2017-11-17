package com.qi.backstage.management.controller;

import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseSystem;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Class MenuController
 *
 * @author 张麒 2017-11-17.
 * @version Description:
 */
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private SystemReadService systemReadService;

    @Autowired
    private MenuReadService readService;

    @Autowired
    private MenuWriteService writeService;

    @GetMapping("index")
    public String index(ModelMap model, BaseMenu menu) {
        List<BaseSystem> systems = systemReadService.findAll(new BaseSystem());
        if (StringUtil.isBlank(menu.getSystem()) && systems.size() > 0) {
            menu.setSystem(systems.get(0).getCode());
        }
        model.put("data", readService.findAll(menu));
        model.put("systems", systems);
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("options", BootstrapUtil.matchOptions("menu_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "menu/index";
    }

    @GetMapping("add")
    public String add(ModelMap model) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("", systemReadService.findAll(new BaseSystem()), "code", "namecn");
        model.put("defaultSel", options.get(0));
        model.put("options", options);
        return "menu/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, String guid) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("", systemReadService.findAll(new BaseSystem()), "code", "namecn");
        model.put("options", options);
        BaseMenu menu = readService.getByGuid(guid);
        model.put("model", menu);
        for (Map<String, Object> option : options) {
            if (option.get("value").equals(menu.getSystem())) {
                model.put("defaultSel", option);
                break;
            }
        }
        return "menu/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseMenu> save(BaseMenu menu) {
        writeService.save(menu);
        return new ActionResult<>(menu);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseMenu> disable(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return new ActionResult<>();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseMenu> valid(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Valid);
        return new ActionResult<>();
    }
}
