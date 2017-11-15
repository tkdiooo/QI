package com.qi.backstage.management.controller;

import com.qi.backstage.management.common.constants.CommonConstants;
import com.qi.backstage.management.rpc.consumer.DictionaryServiceConsumer;
import com.qi.backstage.model.domain.BaseSystem;
import com.qi.backstage.model.dto.DictionaryDto;
import com.qi.bootstrap.constants.BootstrapConstants;
import com.qi.bootstrap.util.BootstrapUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.constants.UIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private DictionaryServiceConsumer serviceConsumer;

    @GetMapping("grid")
    public String grid(ModelMap model, BaseSystem system) {
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        model.put("options", BootstrapUtil.matchOptions("system_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        return "system/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getContent());
        List<DictionaryDto> list = serviceConsumer.findChildByNumber(CommonConstants.DICT_NUMNER_SYSTEM_TYPE);
        List<Map<String, Object>> options;
        if (null != list) {
            options = BootstrapUtil.matchOptions("system_add_options", list, "number", "content");
            model.put("defaultSel", options.get(0));
            options.remove(0);
            model.put("options", options);
        } else {
            options = BootstrapUtil.matchOptions("system_add_default_options", CommonConstants.SystemType.Default);
            model.put("defaultSel", options.get(0));
        }
        return "system/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getContent());
        return "system/edit";
    }
}
