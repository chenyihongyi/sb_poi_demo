package com.poi.demo.poi_demo.mapper;

import com.poi.demo.poi_demo.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/21 0:42
 */
public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectAll(@Param("name") String name);
}
