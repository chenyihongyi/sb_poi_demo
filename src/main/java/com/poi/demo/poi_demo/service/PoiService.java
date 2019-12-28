package com.poi.demo.poi_demo.service;


import com.google.common.base.Strings;
import com.poi.demo.poi_demo.entity.Product;
import com.poi.demo.poi_demo.enums.WorkBookVersion;
import com.poi.demo.poi_demo.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/21 3:07
 */
@Service
public class PoiService {

    private static final Logger log = LoggerFactory.getLogger(PoiService.class);

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 填充数据到excel到sheet中
     * @param dataList
     * @param headers
     * @param sheetName
     */
    public Workbook fillExcelSheetData(List<Map<Integer, Object>> dataList, String [] headers, String sheetName){
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);

        //创建sheet的第一行数据-即excel的头部信息
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i<headers.length; i++){
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        //从第二行开始塞入真正的数据列表
        int rowIndex = 1;
        Row row;
        Object obj;

        if(dataList!=null && dataList.size()>0){
            for(Map<Integer, Object> rowMap:dataList){
                try{
                    row = sheet.createRow(rowIndex++);

                    //遍历表头行->每个key -> 取到实际的value
                    for(int i = 0; i<headers.length; i++){
                        obj = rowMap.get(i);

                        if(obj == null){
                            row.createCell(i).setCellValue("");
                        } else if (obj instanceof Date) {
                            String tempDate = simpleDateFormat.format((Date) obj);
                            row.createCell(i).setCellValue((tempDate == null) ? "" : tempDate);
                        }else{
                            row.createCell(i).setCellValue(String.valueOf(obj));
                        }
                    }
                }catch(Exception e){
                    log.debug("excel sheet填充数据 发生异常: ", e.fillInStackTrace());
                }
            }
        }
        return wb;
    }

    /**
     * 根据file与后缀名区分获取workbook实例
     * @param file
     * @param suffix
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(MultipartFile file, String suffix)throws Exception{
        Workbook wk = null;
        if(WorkBookVersion.WorkBook2003xls.getCode().equalsIgnoreCase(suffix)){
            wk= new HSSFWorkbook(file.getInputStream());
        }else if(WorkBookVersion.WorkBook2007xls.getCode().equalsIgnoreCase(suffix)){
            wk = new XSSFWorkbook(file.getInputStream());
        }
        return wk;
    }

    public List<Product> readExcelData(Workbook wb) throws Exception{
        Product product;

        List<Product> products = new ArrayList<Product>();
        Row row = null;
        int numSheet = wb.getNumberOfSheets();
        if (numSheet > 0) {
            for (int i = 0; i < numSheet; i++) {
                Sheet sheet = wb.getSheetAt(i);
                int numRow = sheet.getLastRowNum();
                if (numRow > 0) {
                    for(int j = 1; j<numRow; j++){
                        //跳过excel sheet 表格头部
                        row = sheet.getRow(j);
                        product = new Product();

                        String name = ExcelUtil.manageCell(row.getCell(0), null);
                        String unit = ExcelUtil.manageCell(row.getCell(1), null);
                        Double price = Double.valueOf(ExcelUtil.manageCell(row.getCell(2), null));
                        String stock = ExcelUtil.manageCell(row.getCell(3), null);
                        String remark = ExcelUtil.manageCell(row.getCell(4), null);

                        product.setName(name);
                        product.setUnit(unit);
                        product.setPrice(price);
                        product.setStock((!Strings.isNullOrEmpty(stock) && stock.contains("."))?
                                Integer.valueOf(stock.substring(0,stock.lastIndexOf("."))):Integer.valueOf(stock));
                       // product.setStock(Integer.valueOf(stock));
                        String value = ExcelUtil.manageCell(row.getCell(5), "yyyy-MM-dd");
                        product.setPurchaseDate(simpleDateFormat.parse(value));
                        product.setRemark(remark);

                        products.add(product);
                    }
                }
            }
        }
        log.error("获取数据列表:{}", products);
        return products;
    }
}
