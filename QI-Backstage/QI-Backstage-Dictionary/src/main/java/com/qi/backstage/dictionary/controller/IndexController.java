package com.qi.backstage.dictionary.controller;

import com.alibaba.fastjson.JSONObject;
import com.qi.backstage.dictionary.service.read.DictionaryReadService;
import com.qi.backstage.dictionary.service.write.DictionaryWriteService;
import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
import com.sfsctech.constants.inf.IEnum;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @GetMapping("query")
    public String query(ModelMap model, BaseDictionary dictionary) {
        model.put("data", readService.findAll(dictionary));
        model.put("options", UIConstants.matchOptions(StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "dictionary/index";
    }

//    @ResponseBody
//    @PostMapping("query")
//    public ActionResult<PagingInfo<DictionaryDto>> getData(PagingInfo<DictionaryDto> pagingInfo) {
//        return new ActionResult<>(readService.findByPage(pagingInfo));
//    }

    @GetMapping("add")
    public String add(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        return "dictionary/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameter("guid"));
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        return "dictionary/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDictionary> save(BaseDictionary dictionary) {
        writeService.save(dictionary);
        return new ActionResult<>(dictionary);
    }
}
