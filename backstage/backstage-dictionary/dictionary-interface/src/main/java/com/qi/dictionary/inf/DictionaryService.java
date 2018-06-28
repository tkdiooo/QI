package com.qi.dictionary.inf;

import com.qi.dictionary.model.dto.DictionaryDto;
import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.constants.StatusConstants;
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

    @RequestMapping("findChildByNumber")
    RpcResult<List<DictionaryDto>> findChildByNumber(String parent);

    @RequestMapping("getByNumber")
    RpcResult<DictionaryDto> getByNumber(String number);

    @RequestMapping("save")
    RpcResult<DictionaryDto> save(DictionaryDto dictionary);

    @RequestMapping("changeStatus")
    void changeStatus(String number, StatusConstants.Status status);

    @RequestMapping("sort")
    void sort(String sortable);

    @RequestMapping("numberIsExist")
    RpcResult numberIsExist(DictionaryDto dictionary);

}
