package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.common.util.DictUtil;
import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.model.domain.BaseMenu;
import com.qi.backstage.management.service.read.ButtonReadService;
import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.management.service.transactional.ButtonTransactionalService;
import com.qi.backstage.management.service.write.ButtonWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
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
    private ButtonReadService readService;

    @Autowired
    private ButtonWriteService writeService;

    @Autowired
    private ButtonTransactionalService transactionalService;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @GetMapping("index")
    public String index(ModelMap model, BaseButton button, String sysguid) {
        // 系统信息GUID
        model.put("system", sysguid);
        // 菜单GUID
        model.put("menu", button.getMenuguid());
        List<Breadcrumb> list;
        // 按钮的Guid不为空，则是二级菜单导航请求
        if (StringUtil.isNotBlank(button.getGuid()) && !CommonConstants.ROOT_GUID.equals(button.getGuid())) {
            // 列表面包屑设置
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取按钮信息
                BaseButton btn = readService.getByGuid(button.getGuid());
                Breadcrumb breadcrumb = new Breadcrumb(btn.getName() + "按钮", "/button/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", btn.getGuid());
                return breadcrumb;
            }, button.getGuid(), button.getParent());
            model.put("parent", button.getGuid());
            model.put("parent_breadcrumbs", button.getGuid());
            button.setParent(button.getGuid());
        }
        // 菜单导航请求
        else {
            // 根据系统Guid获取面包屑
            list = BreadcrumbUtil.buildBreadcrumb(() -> {
                // 获取菜单信息
                BaseMenu menu = menuReadService.getByGuid(button.getMenuguid());
                Breadcrumb breadcrumb = new Breadcrumb(menu.getName() + "菜单", "/button/index", CommonConstants.ROOT_CLASS);
                // 设置上级节点Guid
                breadcrumb.addParams("guid", menu.getGuid());
                return breadcrumb;
            }, button.getMenuguid(), button.getParent());
            model.put("parent", CommonConstants.ROOT_GUID);
            model.put("parent_breadcrumbs", button.getMenuguid());
            button.setParent(CommonConstants.ROOT_GUID);
        }
        model.put("header", list.get(list.size() - 1).getText());
        model.put("breadcrumbs", list);
        model.put("data", readService.findAll(button));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("type", DictUtil.Button.cloumns());
        model.put("small", "按钮列表");
        model.put("options", BootstrapUtil.matchOptions("button_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "button/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseButton button, String sysguid) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getDescription());
        // 获取系统Guid
        model.put("system", sysguid);
        // 获取菜单信息
        model.put("menu", menuReadService.getByGuid(button.getMenuguid()));
        Map<String, Object> defaultSel = new HashMap<>();
        if (CommonConstants.ROOT_GUID.equals(button.getParent())) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            button = readService.getByGuid(button.getParent());
            defaultSel.put("text", button.getName());
            defaultSel.put("value", button.getGuid());
        }
        model.put("defaultSel", defaultSel);
        List<Map<String, Object>> options = DictUtil.Button.options();
        model.put("defaultOptions", options.get(0));
        model.put("options", options);
        return "button/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseButton button, String sysguid) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getDescription());
        // 获取系统信息
        model.put("system", sysguid);
        // 获取菜单信息
        model.put("menu", menuReadService.getByGuid(button.getMenuguid()));
        button = readService.getByGuid(button.getGuid());
        Map<String, Object> defaultSel = new HashMap<>();
        if (CommonConstants.ROOT_GUID.equals(button.getParent())) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            BaseButton parent = readService.getByGuid(button.getParent());
            defaultSel.put("text", parent.getName());
            defaultSel.put("value", parent.getGuid());
        }
        List<Map<String, Object>> options = DictUtil.Button.options();
        for (Map<String, Object> option : options) {
            if (option.get("value").equals(button.getType())) {
                model.put("defaultOptions", option);
                break;
            }
        }
        model.put("options", options);
        model.put("defaultSel", defaultSel);
        model.put("model", button);
        return "button/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseButton> save(BaseButton button) {
        writeService.save(button);
        return ActionResult.forSuccess(button);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseButton> disable(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseButton> valid(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Valid);
        return ActionResult.forSuccess();
    }

    @GetMapping("ordering")
    public String ordering(ModelMap model, BaseButton button) {
        // 获取所有当前节点数据
        model.put("data", readService.findAll(button));
        return "common/sort";
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseButton> load(String guid) {
        return ActionResult.forSuccess(readService.getByGuid(guid));
    }
}
