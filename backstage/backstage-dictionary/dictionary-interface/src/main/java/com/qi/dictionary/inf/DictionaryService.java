package com.qi.dictionary.inf;

import com.qi.dictionary.model.dto.DictionaryDto;
import com.sfsctech.cloud.client.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
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

    @RequestMapping({"findChildByNumber"})
    RpcResult<List<DictionaryDto>> findChildByNumber(String parent);

    @RequestMapping({"getByNumber"})
    RpcResult<DictionaryDto> getByNumber(String number);

}
