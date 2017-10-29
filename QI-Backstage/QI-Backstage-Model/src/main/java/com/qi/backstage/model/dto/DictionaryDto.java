package com.qi.backstage.model.dto;

import com.qi.backstage.model.domain.BaseDictionary;
import com.sfsctech.constants.UIConstants;

/**
 * Class DictionaryDto
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public class DictionaryDto extends BaseDictionary {

    private static final long serialVersionUID = -7182086807817270077L;

    public void setStatusValue(String statusValue) {

    }

    public String getStatusValue() {
        if (null != getStatus()) {
            return UIConstants.DataTable.getStatus(super.getStatus());
        }
        return "";
    }
}
