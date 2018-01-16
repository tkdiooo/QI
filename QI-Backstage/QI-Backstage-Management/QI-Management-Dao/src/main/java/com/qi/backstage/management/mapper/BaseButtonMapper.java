package com.qi.backstage.management.mapper;

import com.qi.backstage.management.model.domain.BaseButton;
import com.qi.backstage.management.model.domain.BaseButtonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseButtonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    long countByExample(BaseButtonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int deleteByExample(BaseButtonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int insert(BaseButton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int insertSelective(BaseButton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    List<BaseButton> selectByExample(BaseButtonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    BaseButton selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BaseButton record, @Param("example") BaseButtonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BaseButton record, @Param("example") BaseButtonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseButton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_button
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BaseButton record);
}