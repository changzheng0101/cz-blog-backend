package com.weixiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author changzheng
 * @date 2026年01月21日 18:22
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.weixiao.mapper"})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }


}
