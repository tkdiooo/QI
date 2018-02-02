package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.model.domain.BaseMenu;
import com.qi.backstage.management.model.domain.BaseSystem;
import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.management.service.transactional.MenuTransactionalService;
import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
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
        model.put("system", menu.getSystem());
        List<Breadcrumb> list;
        // 菜单的Guid不为空，并且不是ROOT。则是二级菜单导航请求
        if (StringUtil.isNotBlank(menu.getGuid()) && !CommonConstants.ROOT_GUID.equals(menu.getGuid())) {
            // 列表面包屑设置
            String menuGuid = menu.getGuid();
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                BaseMenu innerMenu = readService.getByGuid(menuGuid);
                Breadcrumb breadcrumb = new Breadcrumb(innerMenu.getName() + "菜单", "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", innerMenu.getGuid());
                return breadcrumb;
            }, menu.getGuid(), menu.getSystem());
        }
        // 系统级菜单导航请求
        else {
            menu.setGuid(CommonConstants.ROOT_GUID);
            String systemCode = menu.getSystem();
            // 根据系统Guid获取面包屑
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取系统信息
                BaseSystem system = systemReadService.getByGuid(systemCode);
                Breadcrumb breadcrumb = new Breadcrumb(system.getNamecn(), "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", CommonConstants.ROOT_GUID);
                return breadcrumb;
            }, systemCode, CommonConstants.CACHE_SYSTEM_ROOT);
        }
        model.put("header", list.get(list.size() - 1).getText());
        model.put("breadcrumbs", list);
        model.put("parent", menu.getGuid());
        model.put("data", readService.findAll(menu));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("small", "菜单列表");
        model.put("options", BootstrapUtil.matchOptions("menu_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "menu/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseMenu menu) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        // 获取系统信息
        model.put("system", systemReadService.getByGuid(menu.getSystem()));
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
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        // 获取系统信息
        model.put("system", systemReadService.getByGuid(menu.getSystem()));
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
        return new ActionResult<>();
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseMenu> load(String guid) {
        return new ActionResult<>(readService.getByGuid(guid));
    }
}
