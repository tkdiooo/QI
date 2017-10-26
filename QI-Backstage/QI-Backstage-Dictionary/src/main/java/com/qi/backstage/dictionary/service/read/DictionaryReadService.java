package com.qi.backstage.dictionary.service.read;

import com.qi.backstage.model.domain.BaseDictionary;
import com.sfsctech.base.model.PagingInfo;

import java.util.List;

/**
 * Class DictionaryReadService
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public interface DictionaryReadService {

    List<BaseDictionary> find(BaseDictionary condition);

    void findByPage(PagingInfo<BaseDictionary> pagingInfo);

}
