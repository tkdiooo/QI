package com.qi.dictionary.inf;

import com.qi.dictionary.model.dto.DictionaryDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class DictionaryService
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@RestController
@CloudService("dictionary-server")
public interface DictionaryService {

    @RequestMapping("findChildByNumber")
    RpcResult<List<DictionaryDto>> findChildByNumber(@RequestBody DictionaryDto dictionary);

    @RequestMapping("getByNumber")
    RpcResult<DictionaryDto> getByNumber(@RequestBody DictionaryDto dictionary);

    @RequestMapping("save")
    RpcResult<DictionaryDto> save(@RequestBody DictionaryDto dictionary);

    @RequestMapping("changeStatus")
    void changeStatus(@RequestBody DictionaryDto dictionary);

    @RequestMapping("sort")
    void sort(@RequestBody DictionaryDto dictionary);

    @RequestMapping("numberIsExist")
    RpcResult numberIsExist(@RequestBody DictionaryDto dictionary);

}
