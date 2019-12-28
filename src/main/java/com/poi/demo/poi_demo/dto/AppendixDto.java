package com.poi.demo.poi_demo.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/19 0:28
 */
@Data
@ToString
public class AppendixDto implements Serializable {

private String moduleType;

private String location;

private Integer recordId;

}
