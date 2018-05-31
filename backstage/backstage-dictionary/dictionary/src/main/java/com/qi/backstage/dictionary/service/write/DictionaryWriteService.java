package com.qi.backstage.dictionary.service.write;

import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.sfsctech.core.base.constants.StatusConstants;

/**
 * Class DictionaryWriteService
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public interface DictionaryWriteService {

    void save(BaseDictionary dictionary);

    void changeStatus(String number, StatusConstants.Status status);
}
