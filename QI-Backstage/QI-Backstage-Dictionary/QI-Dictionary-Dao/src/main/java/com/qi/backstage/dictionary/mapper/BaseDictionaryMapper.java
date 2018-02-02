package com.qi.backstage.dictionary.mapper;

import com.qi.backstage.dictionary.model.domain.BaseDictionary;
import com.qi.backstage.dictionary.model.domain.BaseDictionaryExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseDictionaryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    long countByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int deleteByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int insert(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int insertSelective(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    List<BaseDictionary> selectByExample(BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    BaseDictionary selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    BaseDictionary selectByGuid(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BaseDictionary record, @Param("example") BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BaseDictionary record, @Param("example") BaseDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BaseDictionary record);
}