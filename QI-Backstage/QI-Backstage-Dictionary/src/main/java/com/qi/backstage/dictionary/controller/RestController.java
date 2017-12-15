package com.qi.backstage.dictionary.controller;

import com.qi.backstage.inf.DictionaryService;
import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.rpc.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Class RestController
 *
 * @author 张麒 2017-12-15.
 * @version Description:
 */
@org.springframework.web.bind.annotation.RestController()
public class RestController {

    @Autowired
    private DictionaryService readService;

    @GetMapping("/rest/{number}")
    public ActionResult<DictionaryDto> rest(@PathVariable(value = "number") String number) {
        return readService.findChildByNumber(number);
    }
}
