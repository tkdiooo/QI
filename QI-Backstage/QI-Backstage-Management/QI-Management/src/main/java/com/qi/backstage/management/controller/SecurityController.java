package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.common.verify.model.VerifyModel;
import com.qi.backstage.management.common.util.BreadcrumbUtil;
import com.qi.backstage.management.common.verify.util.VerifyUtil;
import com.qi.backstage.management.model.domain.BaseDatasource;
import com.qi.backstage.management.service.read.DatasourceReadService;
import com.qi.backstage.management.service.write.DatasourceWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.common.util.FileUtil;
import com.sfsctech.common.util.MapUtil;
import com.sfsctech.constants.JDBCConstants;
import com.sfsctech.constants.PatternConstants;
import com.sfsctech.constants.UIConstants;
import com.sfsctech.database.jdbc.JdbcService;
import com.sfsctech.database.model.DBConfigModel;
import com.sfsctech.database.model.TableModel;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.spring.properties.WebsiteProperties;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private WebsiteProperties properties;

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
    public ActionResult<PagingInfo<BaseDatasource>> getData(@RequestBody PagingInfo<BaseDatasource> pagingInfo) {
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
        List<Map<String, Object>> options = BootstrapUtil.matchOptions("DATABASE_TYPE", JDBCConstants.Driver.MySQL, JDBCConstants.Driver.Oracle);
        datasource = readService.get(datasource.getId());
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
        writeService.save(datasource);
        return new ActionResult<>(datasource);
    }

    @GetMapping("verify")
    public String verify(ModelMap model, BaseDatasource datasource) {
        datasource = readService.get(datasource.getId());
        model.put("databases", JdbcService.showDatabases(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), null, datasource.getUsername(), datasource.getPassword())));
        model.put("datasource", datasource);
        return "security/verify";
    }

    @ResponseBody
    @PostMapping("loadTables")
    public ActionResult<String> loadTables(BaseDatasource datasource, String database) {
        datasource = readService.get(datasource.getId());
        return new ActionResult<>(JdbcService.showTables(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), database, datasource.getUsername(), datasource.getPassword())));
    }

    @GetMapping("descTable")
    public String descTable(ModelMap model, BaseDatasource datasource, String database, String table) {
        datasource = readService.get(datasource.getId());
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
        BaseDatasource datasource = readService.get(vm.getId());
        List<TableModel> tableModels = JdbcService.descTable(new DBConfigModel(datasource.getType(), datasource.getServerip(), datasource.getPort(), vm.getDatabase(), datasource.getUsername(), datasource.getPassword()), vm.getTable());
        return VerifyUtil.BackendVerify(mf, vm.getCondition(), MapUtil.toMap(tableModels, "name"));
    }

    @PostMapping("frontendVerify")
    @ResponseBody
    public ActionResult<String> frontendVerify(@RequestBody VerifyModel vm) {
        BaseDatasource datasource = readService.get(vm.getId());
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
