package com.qi.backstage.dictionary.controller;

import com.alibaba.fastjson.JSONObject;
import com.qi.backstage.dictionary.common.constants.CommonConstants;
import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.dictionary.service.transactional.DictionaryTransactionalService;
import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.bootstrap.breadcrumb.Breadcrumb;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.core.security.annotation.Verify;
import com.sfsctech.core.web.constants.UIConstants;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    private CacheFactory<RedisProxy<String, Object>> factory;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("grid")
    public String grid(ModelMap model, BaseDictionary dictionary) {
        // 父节点parent为空
        if (StringUtil.isBlank(dictionary.getParent())) {
            dictionary.setParent(CommonConstants.ROOT_GUID);
        }
        // 列表面包屑设置
        List<Breadcrumb> list = factory.getList(dictionary.getParent());
        // 缓存为空
        if (list == null) {
            // 根节点为空，设置根节点
            if (CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
                Breadcrumb breadcrumb = new Breadcrumb(CommonConstants.ROOT_NAME, CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", CommonConstants.ROOT_GUID);
                list = new ArrayList<>();
                list.add(breadcrumb);
            } else {
                BaseDictionary dict = readService.getByNumber(dictionary.getParent());
                Breadcrumb breadcrumb = new Breadcrumb(dict.getContent(), CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", dict.getNumber());
                list = factory.getList(dict.getParent());
                list.add(breadcrumb);
            }
            factory.getCacheClient().put(dictionary.getParent(), list);
        }
        model.put("parent", dictionary.getParent());
        model.put("data", readService.findAll(dictionary));
        model.put("options", BootstrapUtil.matchOptions("dictionary_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        model.put("breadcrumbs", list);
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        return "dictionary/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, String parent) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getDescription());
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(parent)) {
            model.put("parent_number", parent);
        }
        // 父节点Number
        model.put("parent", parent);
        return "dictionary/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, String number) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getDescription());
        BaseDictionary dictionary = readService.getByNumber(number);
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
            model.put("parent_number", dictionary.getParent());
            dictionary.setNumber(dictionary.getNumber().substring(dictionary.getNumber().length() - 4));
        }
        // 父节点Number
        model.put("parent", dictionary.getParent());
        model.put("model", dictionary);
        return "dictionary/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDictionary> save(@Verify BaseDictionary dictionary) {
        writeService.save(dictionary);
        return ActionResult.forSuccess(dictionary);
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseDictionary> disable(String number) {
        writeService.changeStatus(number, StatusConstants.Status.Disable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseDictionary> valid(String number) {
        writeService.changeStatus(number, StatusConstants.Status.Valid);
        return ActionResult.forSuccess();
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
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseDictionary> load(String number) {
        BaseDictionary dictionary = readService.getByNumber(number);
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
            dictionary.setNumber(dictionary.getNumber().substring(dictionary.getNumber().length() - 4));
        }
        return ActionResult.forSuccess(dictionary);
    }

    @ResponseBody
    @PostMapping("exist")
    public JSONObject exist(BaseDictionary dictionary) {
        JSONObject json = new JSONObject();
        json.put("valid", readService.numberIsExist(dictionary));
        return json;
    }
}
