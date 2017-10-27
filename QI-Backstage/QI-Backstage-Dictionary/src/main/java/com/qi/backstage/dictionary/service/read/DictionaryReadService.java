package com.qi.backstage.dictionary.service.read;

import com.qi.backstage.model.dto.DictionaryDto;

import java.util.List;

/**
 * Class DictionaryReadService
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public interface DictionaryReadService {

    List<DictionaryDto> findAll();

}
