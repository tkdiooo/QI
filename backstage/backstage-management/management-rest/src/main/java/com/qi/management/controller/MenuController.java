package com.qi.management.controller;

import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.qi.management.common.constants.CommonConstants;
import com.qi.management.common.util.BreadcrumbUtil;
import com.qi.management.model.domain.BaseMenu;
import com.qi.management.model.domain.BaseSystem;
import com.qi.management.service.read.MenuReadService;
import com.qi.management.service.read.SystemReadService;
import com.qi.management.service.transactional.MenuTransactionalService;
import com.qi.management.service.write.MenuWriteService;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.web.constants.UIConstants;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @Autowired
    private MenuTransactionalService transactionalService;

    @GetMapping("index")
    public String index(ModelMap model, BaseMenu menu) {
        // 系统信息GUID
        model.put("system", menu.getSysguid());
        List<Breadcrumb> list;
        // 菜单的Parent不为空，并且不是ROOT。则是二级菜单导航请求
        if (StringUtil.isNotBlank(menu.getParent()) && !CommonConstants.ROOT_GUID.equals(menu.getParent())) {
            // 列表面包屑设置
            String menuGuid = menu.getParent();
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                BaseMenu innerMenu = readService.getByGuid(menuGuid);
                Breadcrumb breadcrumb = new Breadcrumb(innerMenu.getName() + "菜单", "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", innerMenu.getGuid());
                return breadcrumb;
            }, menu.getParent(), menu.getSysguid());
        }
        // 系统级菜单导航请求
        else {
            menu.setParent(CommonConstants.ROOT_GUID);
            String sysGuid = menu.getSysguid();
            // 根据系统Guid获取面包屑
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取系统信息
                BaseSystem system = systemReadService.getByGuid(sysGuid);
                Breadcrumb breadcrumb = new Breadcrumb(system.getNamecn(), "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", CommonConstants.ROOT_GUID);
                return breadcrumb;
            }, sysGuid, CommonConstants.CACHE_SYSTEM_ROOT);
        }
        model.put("header", list.get(list.size() - 1).getText());
        model.put("breadcrumbs", list);
        model.put("parent", menu.getParent());
        model.put("data", readService.findAll(menu));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("small", "菜单列表");
        model.put("options", BootstrapUtil.matchOptions("menu_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "menu/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseMenu menu) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getDescription());
        // 获取系统信息
        model.put("system", systemReadService.getByGuid(menu.getSysguid()));
        Map<String, Object> defaultSel = new HashMap<>();
        if (CommonConstants.ROOT_GUID.equals(menu.getParent())) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            menu = readService.getByGuid(menu.getParent());
            defaultSel.put("text", menu.getName());
            defaultSel.put("value", menu.getGuid());
            model.put("guid", menu.getGuid());
        }
        model.put("defaultSel", defaultSel);
        return "menu/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseMenu menu) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getDescription());
        // 获取系统信息
        model.put("system", systemReadService.getByGuid(menu.getSysguid()));
        menu = readService.getByGuid(menu.getGuid());
        model.put("model", menu);
        Map<String, Object> defaultSel = new HashMap<>();
        if (menu.getParent().equals(CommonConstants.ROOT_GUID)) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            BaseMenu parent = readService.getByGuid(menu.getParent());
            defaultSel.put("text", parent.getName());
            defaultSel.put("value", parent.getGuid());
            model.put("guid", menu.getGuid());
        }
        model.put("defaultSel", defaultSel);
        return "menu/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseMenu> save(BaseMenu menu) {
        writeService.save(menu);
        return ActionResult.forSuccess(menu);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseMenu> disable(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseMenu> valid(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Valid);
        return ActionResult.forSuccess();
    }

    @GetMapping("ordering")
    public String ordering(ModelMap model, BaseMenu menu) {
        // 获取所有当前节点数据
        model.put("data", readService.findAll(menu));
        return "common/sort";
    }

    @ResponseBody
    @PostMapping("sort")
    public ActionResult<String> sort(String sortable) {
        transactionalService.sort(sortable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseMenu> load(String guid) {
        return ActionResult.forSuccess(readService.getByGuid(guid));
    }
}
