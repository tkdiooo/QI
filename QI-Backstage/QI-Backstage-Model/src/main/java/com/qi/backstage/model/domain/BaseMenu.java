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
     * This field corresponds to the database column base_menu.System
     *
     * @mbg.generated
     */
    private String system;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Code
     *
     * @mbg.generated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Name
     *
     * @mbg.generated
     */
    private String name;

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
     * This field corresponds to the database column base_menu.Description
     *
     * @mbg.generated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Sort
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_menu.Status
     *
     * @mbg.generated
     */
    private Integer status;

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
     * This method returns the value of the database column base_menu.System
     *
     * @return the value of base_menu.System
     * @mbg.generated
     */
    public String getSystem() {
        return system;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.System
     *
     * @param system the value for base_menu.System
     * @mbg.generated
     */
    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
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
     * This method returns the value of the database column base_menu.Status
     *
     * @return the value of base_menu.Status
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_menu.Status
     *
     * @param status the value for base_menu.Status
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}