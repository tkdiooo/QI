package com.qi.dictionary.model.dto;


import com.qi.dictionary.model.domain.BaseDictionary;

/**
 * Class DictionaryDto
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public class DictionaryDto extends BaseDictionary {

    private static final long serialVersionUID = -7182086807817270077L;

    private String sortable;

    public String getSortable() {
        return sortable;
    }

    public void setSortable(String sortable) {
        this.sortable = sortable;
    }
}
