package com.poi.demo.poi_demo.service;


import com.poi.demo.poi_demo.dto.AppendixDto;
import com.poi.demo.poi_demo.entity.Appendix;
import com.poi.demo.poi_demo.mapper.AppendixMapper;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/12/19 0:24
 */
@Service
public class AppendixService {

    private static final Logger log = LoggerFactory.getLogger(AppendixService.class);

    @Autowired
    private Environment env;

    @Autowired
    private AppendixMapper appendixMapper;

    public String uploadFile(MultipartFile file, AppendixDto dto) throws Exception {
        if (file == null) {
            throw new RuntimeException("附件为空!");
        }
        String fileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(fileName, fileName.lastIndexOf("."));

        //TODO 定义最终附件存储目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String rootUrl = env.getProperty("file.upload.root.url") + File.separator + dto.getModuleType() + File.separator + dateFormat.format(new Date()) + File.separator;
        File rootFile = new File(rootUrl);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        //构造最终附件名
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String destFileName = dateFormat.format(new Date()) + suffix;
        File destFile = new File(rootUrl + File.separator + destFileName);

        file.transferTo(destFile);

        String location = File.separator + dto.getModuleType() + File.separator + dateFormat.format(new Date()) + File.separator + destFileName;
        return location;
    }

    /**
     * 保存上传附件记录
     */
    public void saveRecord(MultipartFile file, AppendixDto dto){
        Appendix entity = new Appendix();
        BeanUtils.copyProperties(dto, entity);

        entity.setName(file.getOriginalFilename());
        entity.setSize(file.getSize());

        appendixMapper.insertSelective(entity);
    }

}
