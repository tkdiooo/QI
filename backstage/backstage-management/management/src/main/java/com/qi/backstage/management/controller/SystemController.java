package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.common.util.DictUtil;
import com.qi.backstage.management.model.domain.BaseSystem;
import com.qi.backstage.management.service.read.SystemReadService;
import com.qi.backstage.management.service.write.SystemWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
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
 * Class SystemController
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private SystemReadService readService;

    @Autowired
    private SystemWriteService writeService;

    @GetMapping("index")
    public String index(ModelMap model, BaseSystem system) {
        // 列表面包屑设置
        model.put("breadcrumbs", BreadcrumbUtil.buildBreadcrumb(() -> new Breadcrumb(CommonConstants.ROOT_NAME, "/system/index", CommonConstants.ROOT_CLASS), CommonConstants.CACHE_SYSTEM_ROOT));
        model.put("data", readService.findAll(system));
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("options", BootstrapUtil.matchOptions("system_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        model.put("type", DictUtil.System.cloumns());
        return "system/index";
    }

    @GetMapping("add")
    public String add(ModelMap model) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        List<Map<String, Object>> options = DictUtil.System.options();
        model.put("defaultSel", options.get(0));
        model.put("options", options);
        return "system/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, String guid) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        List<Map<String, Object>> options = DictUtil.System.options();
        model.put("options", options);
        BaseSystem system = readService.getByGuid(guid);
        model.put("model", system);
        for (Map<String, Object> option : options) {
            if (option.get("value").equals(system.getType())) {
                model.put("defaultSel", option);
                break;
            }
        }
        if (!model.containsKey("defaultSel")) {
            model.put("defaultSel", options.get(0));
        }
        return "system/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseSystem> save(BaseSystem system) {
        writeService.save(system);
        return new ActionResult<>(system);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseSystem> disable(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return new ActionResult<>();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseSystem> valid(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Valid);
        return new ActionResult<>();
    }
}
