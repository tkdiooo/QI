package com.qi.backstage.dictionary.controller;

import com.qi.backstage.dictionary.inf.DictionaryService;
import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.qi.backstage.dictionary.rpc.consumer.DictionaryServiceConsumer;
import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.core.rpc.result.RpcResult;
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
    private DictionaryServiceConsumer readService;

    @GetMapping("/rest/{number}")
    public ActionResult<List<DictionaryDto>> rest(@PathVariable(value = "number") String number) {
        return readService.findChildByNumber(number);
    }
}
