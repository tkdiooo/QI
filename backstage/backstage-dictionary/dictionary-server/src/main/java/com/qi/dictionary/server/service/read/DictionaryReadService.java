package com.qi.dictionary.server.service.read;


import com.qi.dictionary.model.domain.BaseDictionary;

import java.util.List;

/**
 * Class DictionaryReadService
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public interface DictionaryReadService {

    List<BaseDictionary> findByParent(String parent);

    BaseDictionary getByNumber(String number);

    boolean numberIsExist(BaseDictionary dictionary);
}
