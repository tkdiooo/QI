package com.qi.backstage.dictionary.model.domain;

import com.sfsctech.base.model.BaseDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class BaseDictionary extends BaseDto {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Parent
     *
     * @mbg.generated
     */
    @NotEmpty
    @Length(max = 40)
    @Pattern(regexp = "[0-9]*", message = "数值必须是数字")
    private String parent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Number
     *
     * @mbg.generated
     */
    @NotEmpty
    @Length(min = 4, max = 4)
    @Pattern(regexp = "[0-9]*", message = "数值必须是数字")
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Content
     *
     * @mbg.generated
     */
    @NotEmpty
    @Length(max = 20)
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Pinyin
     *
     * @mbg.generated
     */
    @Length(max = 100)
    @Pattern(regexp = "[a-zA-Z0-9_.^%&',;=?$\\x22]*$", message = "数值必须是英文/特殊字符")
    private String pinyin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.English
     *
     * @mbg.generated
     */
    @Length(max = 100)
    @Pattern(regexp = "[a-zA-Z0-9_.^%&',;=?$\\x22]*$", message = "数值必须是英文/特殊字符")
    private String english;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Description
     *
     * @mbg.generated
     */
    @Length(max = 100)
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Sort
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary.Status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.ID
     *
     * @return the value of base_dictionary.ID
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.ID
     *
     * @param id the value for base_dictionary.ID
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Parent
     *
     * @return the value of base_dictionary.Parent
     * @mbg.generated
     */
    public String getParent() {
        return parent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Parent
     *
     * @param parent the value for base_dictionary.Parent
     * @mbg.generated
     */
    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Number
     *
     * @return the value of base_dictionary.Number
     * @mbg.generated
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Number
     *
     * @param number the value for base_dictionary.Number
     * @mbg.generated
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Content
     *
     * @return the value of base_dictionary.Content
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Content
     *
     * @param content the value for base_dictionary.Content
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Pinyin
     *
     * @return the value of base_dictionary.Pinyin
     * @mbg.generated
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Pinyin
     *
     * @param pinyin the value for base_dictionary.Pinyin
     * @mbg.generated
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.English
     *
     * @return the value of base_dictionary.English
     * @mbg.generated
     */
    public String getEnglish() {
        return english;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.English
     *
     * @param english the value for base_dictionary.English
     * @mbg.generated
     */
    public void setEnglish(String english) {
        this.english = english == null ? null : english.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Description
     *
     * @return the value of base_dictionary.Description
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Description
     *
     * @param description the value for base_dictionary.Description
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Sort
     *
     * @return the value of base_dictionary.Sort
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Sort
     *
     * @param sort the value for base_dictionary.Sort
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary.Status
     *
     * @return the value of base_dictionary.Status
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary.Status
     *
     * @param status the value for base_dictionary.Status
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}