package com.qi.backstage.dictionary.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.dictionary.service.transactional.DictionaryTransactionalService;
import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.RandomUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
public class IndexController {

    @Autowired
    private DictionaryWriteService writeService;

    @Autowired
    private DictionaryReadService readService;

    @Autowired
    private DictionaryTransactionalService transactionalService;

    @Autowired
    private CacheFactory factory;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("grid")
    public String grid(ModelMap model, BaseDictionary dictionary) {
        // 父节点Guid为空
        if (StringUtil.isBlank(dictionary.getParent())) {
            dictionary.setParent("0000000000000000000000");
        }
        // 列表面包屑设置
        List<Breadcrumb> list = factory.getList(dictionary.getParent());
        // 缓存为空
        if (list == null) {
            // 根节点为空，设置根节点
            if ("0000000000000000000000".equals(dictionary.getParent())) {
                list = new ArrayList<>();
                list.add(new Breadcrumb("Root", "0000000000000000000000", "active"));
            } else {
                BaseDictionary dict = readService.getByGuid(dictionary.getParent());
                list = factory.getList(dict.getParent());
                list.add(new Breadcrumb(dict.getContent(), dict.getGuid(), "active"));
            }
            factory.getCacheClient().put(dictionary.getParent(), list);
        }
        model.put("data", readService.findAll(dictionary));
        model.put("parent", dictionary.getParent());
        model.put("options", BootstrapUtil.matchOptions("dictionary_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        model.put("breadcrumbs", list);
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        return "dictionary/index";
    }

//    @ResponseBody
//    @PostMapping("query")
//    public ActionResult<PagingInfo<DictionaryDto>> getData(PagingInfo<DictionaryDto> pagingInfo) {
//        return new ActionResult<>(readService.findByPage(pagingInfo));
//    }

    @GetMapping("add")
    public String add(ModelMap model, BaseDictionary dictionary) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        // 父节点Guid
        model.put("parent_guid", dictionary.getParent());
        // 不是跟节点的情况下，获取父节点编号
        if (!"0000000000000000000000".equals(dictionary.getParent())) {
            model.put("parent_number", readService.getByGuid(dictionary.getParent()).getNumber());
        }
        return "dictionary/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, BaseDictionary dictionary) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        BaseDictionary dict = readService.getByGuid(dictionary.getGuid());
        // 父节点Guid
        model.put("parent_guid", dict.getParent());
        // 不是跟节点的情况下，获取父节点编号
        if (!"0000000000000000000000".equals(dict.getParent())) {
            model.put("parent_number", dict.getNumber().substring(5));
        }
        dict.setNumber(dict.getNumber().substring(dict.getNumber().length() - 5));
        model.put("model", dict);
        return "dictionary/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDictionary> save(BaseDictionary dictionary) {
        writeService.save(dictionary);
        return new ActionResult<>(dictionary);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseDictionary> disable(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Disable);
        return new ActionResult<>();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseDictionary> valid(String guid) {
        writeService.changeStatus(guid, StatusConstants.Status.Valid);
        return new ActionResult<>();
    }

    @GetMapping("ordering")
    public String ordering(ModelMap model, BaseDictionary dictionary) {
        // 获取所有当前节点数据
        model.put("data", readService.findAll(dictionary));
        return "dictionary/sort";
    }

    @ResponseBody
    @PostMapping("sort")
    public ActionResult<String> sort(String sortable) {
        transactionalService.sort(sortable);
        return new ActionResult<>();
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseDictionary> load(String guid) {
        return new ActionResult<>(readService.getByGuid(guid));
    }
}
