package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.common.util.DictUtil;
import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.model.domain.BaseSystem;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class DataSecurityController
 *
 * @author 张麒 2018-3-6.
 * @version Description:
 */
@Controller
@RequestMapping("security")
public class SecurityController {

    @GetMapping("index")
    public String index(ModelMap model) {
        // 列表面包屑设置
        model.put("breadcrumbs", BreadcrumbUtil.buildBreadcrumb(() -> new Breadcrumb("数据源", "/security/index", CommonConstants.ROOT_CLASS), CommonConstants.CACHE_SECURITY_ROOT));
//        model.put("data", readService.findAll(system));
//        model.put("status", BootstrapConstants.StatusColumns.getColumns());
//        model.put("options", BootstrapUtil.matchOptions("system_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
//        model.put("type", DictUtil.System.cloumns());
        return "security/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, BaseDatasource datasource) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
//        // 获取系统信息
//        model.put("system", systemReadService.getByGuid(menu.getSysguid()));
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
        return "security/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseDatasource datasource) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
//        // 获取系统信息
//        model.put("system", systemReadService.getByGuid(menu.getSysguid()));
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
        return "security/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDatasource> save(BaseDatasource datasource) {
//        writeService.save(menu);
        return new ActionResult<>(datasource);
    }
}
