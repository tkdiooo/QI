package com.qi.backstage.dictionary.service.read;


import com.qi.backstage.dictionary.model.domain.BaseDictionary;

import java.util.List;

/**
 * Class DictionaryReadService
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public interface DictionaryReadService {

    List<BaseDictionary> findAll(BaseDictionary model);

    BaseDictionary getByGuid(String guid);

    boolean numberIsExist(String number);
}
