package com.qi.backstage.dictionary.inf;

import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.sfsctech.core.rpc.result.RpcResult;

import java.util.List;

/**
 * Class DictionaryService
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
public interface DictionaryService {

    RpcResult<List<DictionaryDto>> findChildByNumber(String number);

}
