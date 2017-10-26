package com.qi.backstage.dictionary.service.read;

import com.qi.backstage.model.domain.BaseDictionary;
import com.qi.backstage.model.dto.DictionaryDto;
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

    PagingInfo<DictionaryDto> findByPage(PagingInfo<DictionaryDto> pagingInfo);

}
