package com.qi.management.model.dto;

import com.qi.management.model.domain.BaseSystem;

import java.util.List;

/**
 * Class SystemDto
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
public class SystemDto extends BaseSystem {

    private List<MenuDto> child;

    public List<MenuDto> getChild() {
        return child;
    }

    public void setChild(List<MenuDto> child) {
        this.child = child;
    }
}
