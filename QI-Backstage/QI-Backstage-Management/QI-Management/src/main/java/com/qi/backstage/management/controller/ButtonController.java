package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.model.domain.BaseMenu;
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

import java.util.List;

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
    private MenuReadService readService;

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
            list = factory.getList(button.getGuid());
            // 缓存为空
            if (list == null) {
                // 获取系统信息节点
//                list = factory.getList(menu.getSystem());
//                menu = readService.getByGuid(menu.getGuid());
//                Breadcrumb breadcrumb = new Breadcrumb(menu.getName() + "菜单", "/menu/index", CommonConstants.ROOT_CLASS);
//                breadcrumb.addParams("guid", menu.getGuid());
//                list.add(breadcrumb);
//                factory.getCacheClient().put(menu.getGuid(), list);
            }
        }
        // 菜单导航请求
        else {
            // 根据系统Guid获取面包屑
            list = factory.getList(button.getMenu());
            String header;
            // 缓存为空，添加当前菜单节点
            if (list == null) {
                // 获取菜单信息
                BaseMenu menu = readService.getByGuid(button.getMenu());
                list = factory.getList(menu.getParent());
                Breadcrumb breadcrumb = new Breadcrumb(menu.getName(), "/menu/index", CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", menu.getGuid());
                list.add(breadcrumb);
                factory.getCacheClient().put(menu.getGuid(), list);
                header = menu.getName() + "菜单";
            } else {
                Breadcrumb breadcrumb = list.get(list.size() - 1);
                header = breadcrumb.getText() + "菜单";
            }
            model.put("header", header);
        }
        model.put("breadcrumbs", list);
        model.put("parent", button.getMenu());
//        model.put("data", readService.findAll(menu));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("small", "按钮列表");
        model.put("options", BootstrapUtil.matchOptions("button_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "button/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseButton button) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        // 获取系统信息
//        model.put("system", systemReadService.getByGuid(menu.getSystem()));
//        Map<String, Object> defaultSel = new HashMap<>();
//        if (CommonConstants.ROOT_GUID.equals(menu.getParent())) {
//            defaultSel.put("text", CommonConstants.ROOT_NAME);
//            defaultSel.put("value", CommonConstants.ROOT_GUID);
//        } else {
//            menu = readService.getByGuid(menu.getParent());
//            defaultSel.put("text", menu.getName());
//            defaultSel.put("value", menu.getGuid());
//            model.put("guid", menu.getGuid());
//        }
//        model.put("defaultSel", defaultSel);
        return "button/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseButton menu) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        // 获取系统信息
//        model.put("system", systemReadService.getByGuid(menu.getSystem()));
//        menu = readService.getByGuid(menu.getGuid());
//        model.put("model", menu);
//        Map<String, Object> defaultSel = new HashMap<>();
//        if (menu.getParent().equals(CommonConstants.ROOT_GUID)) {
//            defaultSel.put("text", CommonConstants.ROOT_NAME);
//            defaultSel.put("value", CommonConstants.ROOT_GUID);
//        } else {
//            BaseMenu parent = readService.getByGuid(menu.getParent());
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
