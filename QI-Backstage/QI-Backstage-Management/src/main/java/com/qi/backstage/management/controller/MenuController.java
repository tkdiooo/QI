package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.service.read.MenuReadService;
import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.management.service.write.MenuWriteService;
import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseSystem;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
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
    private CacheFactory factory;

    @GetMapping("index")
    public String index(ModelMap model, BaseMenu menu) {
        // 系统信息GUID
        model.put("system", menu.getSystem());
        List<Breadcrumb> list;
        // 系统信息页面进入
        if (StringUtil.isBlank(menu.getGuid())) {
            // 列表面包屑设置
            list = factory.getList(menu.getSystem());
            // 缓存为空
            if (list == null) {
                // 获取ROOT节点
                list = factory.getList(CommonConstants.CACHE_SYSTEM_ROOT);
                // 获取系统信息
                BaseSystem system = systemReadService.getByGuid(menu.getSystem());
                list.add(new Breadcrumb(system.getNamecn(), menu.getGuid(), CommonConstants.ROOT_CLASS));
                factory.getCacheClient().put(system.getGuid(), list);
            }
        } else {
            // 列表面包屑设置
            list = factory.getList(menu.getGuid());
            // 缓存为空
            if (list == null) {
                // 获取系统信息节点
                list = factory.getList(menu.getSystem());
                menu = readService.getByGuid(menu.getGuid());
                list.add(new Breadcrumb(menu.getName() + "菜单", menu.getGuid(), CommonConstants.ROOT_CLASS));
                factory.getCacheClient().put(menu.getGuid(), list);
            }
        }
        model.put("breadcrumbs", list);
        model.put("parent", menu.getGuid());
        model.put("data", readService.findAll(menu));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("options", BootstrapUtil.matchOptions("menu_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "menu/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseMenu menu) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        // 获取系统信息
        model.put("system", systemReadService.getByGuid(menu.getSystem()));
        Map<String, Object> defaultSel = new HashMap<>();
        if (StringUtil.isBlank(menu.getParent())) {
            defaultSel.put("text", CommonConstants.ROOT_NAME);
            defaultSel.put("value", CommonConstants.ROOT_GUID);
        } else {
            menu = readService.getByGuid(menu.getParent());
            defaultSel.put("text", menu.getName());
            defaultSel.put("value", menu.getGuid());
        }
        model.put("defaultSel", defaultSel);
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
