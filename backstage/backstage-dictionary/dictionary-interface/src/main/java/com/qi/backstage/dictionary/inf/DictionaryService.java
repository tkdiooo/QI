package com.qi.backstage.dictionary.inf;

import com.qi.backstage.dictionary.model.dto.DictionaryDto;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class DictionaryService
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
public interface DictionaryService {

    ActionResult<DictionaryDto> findChildByNumber(String number);

}