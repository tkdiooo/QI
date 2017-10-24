package com.qi.backstage.mapper;

import com.qi.backstage.model.domain.BaseMenu;
import com.qi.backstage.model.domain.BaseMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    long countByExample(BaseMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int deleteByExample(BaseMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int insert(BaseMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int insertSelective(BaseMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    List<BaseMenu> selectByExample(BaseMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    BaseMenu selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BaseMenu record, @Param("example") BaseMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BaseMenu record, @Param("example") BaseMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BaseMenu record);
}