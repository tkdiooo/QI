package com.qi.dictionary.controller;

import com.qi.dictionary.model.dto.DictionaryDto;
import com.qi.dictionary.rpc.DictionaryConsumer;
import com.sfsctech.core.web.domain.result.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Class RestController
 *
 * @author 张麒 2017-12-15.
 * @version Description:
 */
@org.springframework.web.bind.annotation.RestController()
public class RestController {

    @Autowired
    private DictionaryConsumer readService;

    @GetMapping("/rest/{number}")
    public ActionResult<List<DictionaryDto>> rest(@PathVariable(value = "number") String number) {
        return ActionResult.forSuccess(readService.findChildByNumber(number));
    }
}
