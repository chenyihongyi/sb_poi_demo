package com.poi.demo.poi_demo.mapper;

import com.poi.demo.poi_demo.entity.Appendix;

public interface AppendixMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appendix record);

    int insertSelective(Appendix record);

    Appendix selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appendix record);

    int updateByPrimaryKey(Appendix record);
}