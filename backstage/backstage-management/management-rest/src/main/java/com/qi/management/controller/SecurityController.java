package com.qi.management.controller;

import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.util.BootstrapUtil;
import com.qi.management.common.verify.util.VerifyUtil;
import com.qi.management.rpc.DatasourceConsumer;
import com.qi.management.common.constants.CommonConstants;
import com.qi.management.common.util.BreadcrumbUtil;
import com.qi.management.common.verify.model.VerifyModel;
import com.qi.management.model.domain.BaseDatasource;
import com.sfsctech.core.base.constants.PatternConstants;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.web.constants.UIConstants;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.data.jdbc.JdbcService;
import com.sfsctech.data.jdbc.constants.JDBCConstants;
import com.sfsctech.data.jdbc.model.DBConfigModel;
import com.sfsctech.data.jdbc.model.TableModel;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private DatasourceConsumer datasourceConsumer;

    @Autowired
    private WebsiteProperties properties;

    @GetMapping("index")
    public String index(ModelMap model) {
        // 列表面包屑设置
        model.put("breadcrumbs", BreadcrumbUtil.buildBreadcrumb(() -> new Breadcrumb("数据源", "/security/index", CommonConstants.ROOT_CLASS), CommonConstants.CACHE_SECURITY_ROOT));
//        model.put("data", datasourceConsumer.findAll(system));
//        model.put("status", BootstrapConstants.StatusColumns.getColumns());
//        model.put("options", BootstrapUtil.matchOptions("system_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
//        model.put("type", DictUtil.System.cloumns());
        return "security/index";
    }

    @ResponseBody
    @PostMapping("query")
    public ActionResult<PagingInfo<BaseDatasource>> getData(@RequestBody PagingInfo<BaseDatasource> pagingInfo) {
        return ActionResult.forSuccess(datasourceConsumer.findByPage(pagingInfo));
    }

    @GetMapping("add")
    public String add(ModelMap model) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getDescription());
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("DATABASE_TYPE", JDBCConstants.Driver.MySQL, JDBCConstants.Driver.Oracle);
        model.put("defaultSel", options.get(0));
        model.put("options", options);
        return "security/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseDatasource datasource) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getDescription());
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("DATABASE_TYPE", JDBCConstants.Driver.MySQL, JDBCConstants.Driver.Oracle);
        datasource = datasourceConsumer.get(datasource.getId());
        model.put("model", datasource);
        for (Map<String, Object> option : options) {
            if (option.get("value").equals(datasource.getType())) {
                model.put("defaultSel", option);
                break;
            }
        }
        model.put("options", options);
        return "security/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDatasource> save(BaseDatasource datasource) {
        return ActionResult.forSuccess(datasourceConsumer.save(datasource));
    }

    @GetMapping("verify")
    public String verify(ModelMap model, BaseDatasource datasource) {
        datasource = datasourceConsumer.get(datasource.getId());
        model.put("databases", JdbcService.showDatabases(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), null, datasource.getUsername(), datasource.getPassword())));
        model.put("datasource", datasource);
        return "security/verify";
    }

    @ResponseBody
    @PostMapping("loadTables")
    public ActionResult<List<String>> loadTables(BaseDatasource datasource, String database) {
        datasource = datasourceConsumer.get(datasource.getId());
        return ActionResult.forSuccess(JdbcService.showTables(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), database, datasource.getUsername(), datasource.getPassword())));
    }

    @GetMapping("descTable")
    public String descTable(ModelMap model, BaseDatasource datasource, String database, String table) {
        datasource = datasourceConsumer.get(datasource.getId());
        model.put("data", JdbcService.descTable(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), database, datasource.getUsername(), datasource.getPassword()), table));
        Map<String, String> pattern = PatternConstants.Pattern.getColumns();
        pattern.put("custom", "自定义");
        model.put("patterns", pattern);
        model.put("database", database);
        model.put("table", table);
        return "security/descTable";
    }

    @PostMapping("backendVerify")
    @ResponseBody
    public ActionResult<String> backendVerify(@RequestParam(value = "fileUpload") MultipartFile mf, VerifyModel vm) {
        BaseDatasource datasource = datasourceConsumer.get(vm.getId());
        List<TableModel> tableModels = JdbcService.descTable(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), vm.getDatabase(), datasource.getUsername(), datasource.getPassword()), vm.getTable());
        return VerifyUtil.BackendVerify(mf, vm.getCondition(), MapUtil.toMap(tableModels, "name"));
    }

    @PostMapping("frontendVerify")
    @ResponseBody
    public ActionResult<String> frontendVerify(@RequestBody VerifyModel vm) {
        BaseDatasource datasource = datasourceConsumer.get(vm.getId());
        List<TableModel> tableModels = JdbcService.descTable(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), vm.getDatabase(), datasource.getUsername(), datasource.getPassword()), vm.getTable());
        return VerifyUtil.FrontendVerify(vm.getCondition(), MapUtil.toMap(tableModels, "name"));
    }

    @RequestMapping("downloadVerify")
    public ResponseEntity<byte[]> downloadVerify(String fileName) throws IOException {
        File file = new File(properties.getSupport().getUploadPath().get("VerifyFilePath") + fileName);
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(FileUtil.readFileToByteArray(file), headers, HttpStatus.OK);
        }
        return null;
    }
}
