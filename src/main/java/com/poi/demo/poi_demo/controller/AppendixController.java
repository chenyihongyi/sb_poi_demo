package com.poi.demo.poi_demo.controller;

import com.google.common.base.Strings;
import com.poi.demo.poi_demo.dto.AppendixDto;
import com.poi.demo.poi_demo.enums.StatusCode;
import com.poi.demo.poi_demo.response.BaseResponse;
import com.poi.demo.poi_demo.service.AppendixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.awt.*;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/18 0:03
 */
@RestController
public class AppendixController {

    private static final Logger log = LoggerFactory.getLogger(AppendixController.class);

    private static final String prefix = "appendix";

    @Autowired
    private AppendixService appendixService;

    @RequestMapping(value = prefix+"/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse upload(MultipartHttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            String moduleType = request.getParameter("moduleType");
            if(Strings.isNullOrEmpty(moduleType)){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            MultipartFile file = request.getFile("fileName");
            if(file == null){
               return new BaseResponse(StatusCode.Invalid_Params);
            }
            AppendixDto dto = new AppendixDto();
            dto.setModuleType(moduleType);

            String recordId = request.getParameter("recordId");

            // TODO 通用上传服务
            final String location = appendixService.uploadFile(file, dto);
            log.info("该附件最终上传位置: {} ", location);

            // TODO 保存上传附件记录
            dto.setLocation(location);
            dto.setRecordId(Integer.valueOf(recordId));
            appendixService.saveRecord(file, dto);

            //TODO 更新上传记录的附件的位置


        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail);
            log.error(e.getMessage());
        }
        return response;
    }
}
