package com.qi.management.model.dto;

import com.qi.management.model.domain.BaseMenu;

import java.util.List;

/**
 * Class BaseMenuDto
 *
 * @author 张麒 2017/10/23.
 * @version Description:
 */
public class MenuDto extends BaseMenu {

    private static final long serialVersionUID = -5252560452584476498L;

    private boolean choose;

    private String domain;

    private List<MenuDto> child;

    private String sortable;

    public boolean getChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<MenuDto> getChild() {
        return child;
    }

    public void setChild(List<MenuDto> child) {
        this.child = child;
    }

    public String getSortable() {
        return sortable;
    }

    public void setSortable(String sortable) {
        this.sortable = sortable;
    }
}
