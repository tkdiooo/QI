package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.model.domain.BaseMenu;
import com.qi.backstage.management.service.read.ButtonReadService;
import com.qi.backstage.management.service.read.MenuReadService;
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
 * Class ButtonController
 *
 * @author 张麒 2017-12-25.
 * @version Description:
 */
@Controller
@RequestMapping("button")
public class ButtonController {

    @Autowired
    private MenuReadService menuReadService;

    @Autowired
    private ButtonReadService buttonReadService;

    @Autowired
    private CacheFactory<IRedisService<String, Object>> factory;

    @GetMapping("index")
    public String index(ModelMap model, BaseButton button, String system) {
        // 系统信息GUID
        model.put("system", system);
        // 菜单GUID
        model.put("menu", button.getMenu());
        List<Breadcrumb> list;
        // 按钮的Guid不为空，则是二级菜单导航请求
        if (StringUtil.isNotBlank(button.getGuid())) {
            // 列表面包屑设置
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取菜单信息
                BaseMenu menu = menuReadService.getByGuid(button.getMenu());
                Breadcrumb breadcrumb = new Breadcrumb(menu.getName() + "菜单", "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", menu.getGuid());
                return breadcrumb;
            }, button.getGuid(), button.getParent());
            model.put("parent", button.getParent());
        }
        // 菜单导航请求
        else {
            // 根据系统Guid获取面包屑
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取菜单信息
                BaseMenu menu = menuReadService.getByGuid(button.getMenu());
                Breadcrumb breadcrumb = new Breadcrumb(menu.getName() + "菜单", "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", menu.getGuid());
                return breadcrumb;
            }, button.getMenu(), button.getParent());
            model.put("parent", CommonConstants.ROOT_GUID);
        }
        model.put("header", list.get(list.size() - 1).getText());
        model.put("breadcrumbs", list);
//        model.put("data", menuReadService.findAll(menu));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("small", "按钮列表");
        model.put("options", BootstrapUtil.matchOptions("button_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "button/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseButton button, String system) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        // 获取系统Guid
        model.put("system", system);
        // 获取菜单信息
        model.put("menu", menuReadService.getByGuid(button.getMenu()));
        Map<String, Object> defaultSel = new HashMap<>();
        if (CommonConstants.ROOT_GUID.equals(button.getParent())) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            button = buttonReadService.getByGuid(button.getParent());
            defaultSel.put("text", button.getName());
            defaultSel.put("value", button.getGuid());
//            model.put("guid", menu.getGuid());
        }
        model.put("defaultSel", defaultSel);
        return "button/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseButton menu) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        // 获取系统信息
//        model.put("system", systemReadService.getByGuid(menu.getSystem()));
//        menu = menuReadService.getByGuid(menu.getGuid());
//        model.put("model", menu);
//        Map<String, Object> defaultSel = new HashMap<>();
//        if (menu.getParent().equals(CommonConstants.ROOT_GUID)) {
//            defaultSel.put("text", CommonConstants.ROOT_NAME);
//            defaultSel.put("value", CommonConstants.ROOT_GUID);
//        } else {
//            BaseMenu parent = menuReadService.getByGuid(menu.getParent());
//            defaultSel.put("text", parent.getName());
//            defaultSel.put("value", parent.getGuid());
//            model.put("guid", menu.getGuid());
//        }
//        model.put("defaultSel", defaultSel);
        return "button/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseButton> save(BaseButton menu) {
//        writeService.save(menu);
        return new ActionResult<>(menu);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseButton> disable(String guid) {
//        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return new ActionResult<>();
    }
}
