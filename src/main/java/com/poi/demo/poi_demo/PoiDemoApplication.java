package com.poi.demo.poi_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.poi.demo.poi_demo.mapper")
public class PoiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiDemoApplication.class, args);
    }

}
