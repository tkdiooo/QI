package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.service.read.DatasourceReadService;
import com.qi.backstage.management.service.write.DatasourceWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.constants.JDBCConstants;
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
 * Class DataSecurityController
 *
 * @author 张麒 2018-3-6.
 * @version Description:
 */
@Controller
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private DatasourceReadService readService;

    @Autowired
    private DatasourceWriteService writeService;

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

    @ResponseBody
    @PostMapping("query")
    public ActionResult<PagingInfo<BaseDatasource>> getData(PagingInfo<BaseDatasource> pagingInfo) {
        return new ActionResult<>(readService.findByPage(pagingInfo));
    }

    @GetMapping("add")
    public String add(ModelMap model) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("DATABASE_TYPE", JDBCConstants.Driver.MySQL, JDBCConstants.Driver.Oracle);
        model.put("defaultSel", options.get(0));
        model.put("options", options);
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
        writeService.save(datasource);
        return new ActionResult<>(datasource);
    }
}
