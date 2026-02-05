package com.weixiao.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSourceConfigChecker implements CommandLineRunner {

    // 使用 @Value 注入解析后的值
    @Value("${spring.datasource.url:未配置}")
    private String url;

    @Value("${spring.datasource.username:未配置}")
    private String username;

    // 密码建议在生产环境脱敏
    @Value("${spring.datasource.password:未配置}")
    private String password;

    @Override
    public void run(String... args) {
        log.info("========== 数据源配置检查 ==========");
        log.info("Datasource URL: {}", url);
        log.info("Datasource Username: {}", username);
        // 为了安全，生产环境建议只打印长度或前几位
        log.info("Datasource Password: {}", maskPassword(password));
        log.info("==================================");
    }

    private String maskPassword(String pwd) {
        if (pwd == null || pwd.length() < 2) return "******";
        return pwd.substring(0, 1) + "****" + pwd.substring(pwd.length() - 1);
    }
}