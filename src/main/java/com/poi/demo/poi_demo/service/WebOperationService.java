package com.poi.demo.poi_demo.service;

import com.google.common.base.Strings;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/23 2:49
 */
@Service
public class WebOperationService {

    private static final Logger log = LoggerFactory.getLogger(WebOperationService.class);

    /**
     * 通用下载附件
     * @param response
     * @param is
     * @param fileName
     * @throws Exception
     */
    public void downloadFile(HttpServletResponse response, InputStream is, String fileName) throws Exception {
        if (is == null || Strings.isNullOrEmpty(fileName)) {
            return;
        }

        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            byte[] buffer = new byte[1024];
            int len = bis.read(buffer);
            while (len != -1) {
                bos.write(buffer, 0, len);
                len = bis.read(buffer);
            }
            bos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if(bis !=null){
                try{
                   bis.close();
                }catch (IOException e){}
            }
            if(is !=null){
                try{
                    is.close();
                }catch (IOException e){}
            }
        }
    }

    /**
     * 下载Excel
     * @param response
     * @param wb
     * @param fileName
     * @throws Exception
     */
    public void downloadExcel(HttpServletResponse response, Workbook wb, String fileName)throws Exception{
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso-8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }
}
