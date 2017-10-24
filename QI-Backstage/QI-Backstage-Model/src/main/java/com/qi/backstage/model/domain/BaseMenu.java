package com.qi.backstage.model.domain;

import com.sfsctech.base.model.BaseDto;

public class BaseMenu extends BaseDto {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Guid
     *
     * @mbg.generated
     */
    private String guid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.GuidParent
     *
     * @mbg.generated
     */
    private String guidparent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.GuidRoot
     *
     * @mbg.generated
     */
    private String guidroot;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.CodeSystem
     *
     * @mbg.generated
     */
    private String codesystem;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Code
     *
     * @mbg.generated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.NameParent
     *
     * @mbg.generated
     */
    private String nameparent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.ContextPath
     *
     * @mbg.generated
     */
    private String contextpath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Controller
     *
     * @mbg.generated
     */
    private String controller;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Mapping
     *
     * @mbg.generated
     */
    private String mapping;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.IconPath
     *
     * @mbg.generated
     */
    private String iconpath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Description
     *
     * @mbg.generated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Level
     *
     * @mbg.generated
     */
    private Integer level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Sort
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.IsLeaf
     *
     * @mbg.generated
     */
    private Boolean isleaf;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Status
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.ID
     *
     * @return the value of base_menu.ID
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.ID
     *
     * @param id the value for base_menu.ID
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Guid
     *
     * @return the value of base_menu.Guid
     * @mbg.generated
     */
    public String getGuid() {
        return guid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Guid
     *
     * @param guid the value for base_menu.Guid
     * @mbg.generated
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.GuidParent
     *
     * @return the value of base_menu.GuidParent
     * @mbg.generated
     */
    public String getGuidparent() {
        return guidparent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.GuidParent
     *
     * @param guidparent the value for base_menu.GuidParent
     * @mbg.generated
     */
    public void setGuidparent(String guidparent) {
        this.guidparent = guidparent == null ? null : guidparent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.GuidRoot
     *
     * @return the value of base_menu.GuidRoot
     * @mbg.generated
     */
    public String getGuidroot() {
        return guidroot;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.GuidRoot
     *
     * @param guidroot the value for base_menu.GuidRoot
     * @mbg.generated
     */
    public void setGuidroot(String guidroot) {
        this.guidroot = guidroot == null ? null : guidroot.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.CodeSystem
     *
     * @return the value of base_menu.CodeSystem
     * @mbg.generated
     */
    public String getCodesystem() {
        return codesystem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.CodeSystem
     *
     * @param codesystem the value for base_menu.CodeSystem
     * @mbg.generated
     */
    public void setCodesystem(String codesystem) {
        this.codesystem = codesystem == null ? null : codesystem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Code
     *
     * @return the value of base_menu.Code
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Code
     *
     * @param code the value for base_menu.Code
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.NameParent
     *
     * @return the value of base_menu.NameParent
     * @mbg.generated
     */
    public String getNameparent() {
        return nameparent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.NameParent
     *
     * @param nameparent the value for base_menu.NameParent
     * @mbg.generated
     */
    public void setNameparent(String nameparent) {
        this.nameparent = nameparent == null ? null : nameparent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Name
     *
     * @return the value of base_menu.Name
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Name
     *
     * @param name the value for base_menu.Name
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.ContextPath
     *
     * @return the value of base_menu.ContextPath
     * @mbg.generated
     */
    public String getContextpath() {
        return contextpath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.ContextPath
     *
     * @param contextpath the value for base_menu.ContextPath
     * @mbg.generated
     */
    public void setContextpath(String contextpath) {
        this.contextpath = contextpath == null ? null : contextpath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Controller
     *
     * @return the value of base_menu.Controller
     * @mbg.generated
     */
    public String getController() {
        return controller;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Controller
     *
     * @param controller the value for base_menu.Controller
     * @mbg.generated
     */
    public void setController(String controller) {
        this.controller = controller == null ? null : controller.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Mapping
     *
     * @return the value of base_menu.Mapping
     * @mbg.generated
     */
    public String getMapping() {
        return mapping;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Mapping
     *
     * @param mapping the value for base_menu.Mapping
     * @mbg.generated
     */
    public void setMapping(String mapping) {
        this.mapping = mapping == null ? null : mapping.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.IconPath
     *
     * @return the value of base_menu.IconPath
     * @mbg.generated
     */
    public String getIconpath() {
        return iconpath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.IconPath
     *
     * @param iconpath the value for base_menu.IconPath
     * @mbg.generated
     */
    public void setIconpath(String iconpath) {
        this.iconpath = iconpath == null ? null : iconpath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Description
     *
     * @return the value of base_menu.Description
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Description
     *
     * @param description the value for base_menu.Description
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Level
     *
     * @return the value of base_menu.Level
     * @mbg.generated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Level
     *
     * @param level the value for base_menu.Level
     * @mbg.generated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Sort
     *
     * @return the value of base_menu.Sort
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Sort
     *
     * @param sort the value for base_menu.Sort
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.IsLeaf
     *
     * @return the value of base_menu.IsLeaf
     * @mbg.generated
     */
    public Boolean getIsleaf() {
        return isleaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.IsLeaf
     *
     * @param isleaf the value for base_menu.IsLeaf
     * @mbg.generated
     */
    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_menu.Status
     *
     * @return the value of base_menu.Status
     * @mbg.generated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Status
     *
     * @param status the value for base_menu.Status
     * @mbg.generated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}