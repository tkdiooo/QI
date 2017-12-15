package com.qi.backstage.inf;

import com.qi.backstage.model.dto.DictionaryDto;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class DictionaryService
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
public interface DictionaryService {

//    ActionResult<DictionaryDto> getByGuid(String guid);

    ActionResult<DictionaryDto> findChildByNumber(String number);
}
