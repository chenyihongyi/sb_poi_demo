package com.poi.demo.poi_demo.enums;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/26 21:53
 */
public enum WorkBookVersion {

    WorkBook2003xls("xls", "xls后缀名结尾-2003版本workBook"),
    WorkBook2007xls("xlsx", "xlsx后缀名结尾-2007版本workBook");

    private String code;

    private String name;

    WorkBookVersion(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public static WorkBookVersion valueOfSuffix(String suffix){
        if(WorkBook2003xls.getCode().equalsIgnoreCase(suffix)){
            return WorkBook2003xls;
        }else if(WorkBook2007xls.getCode().equalsIgnoreCase(suffix)){
            return WorkBook2007xls;
        }else{
            return null;
        }
    }
}
