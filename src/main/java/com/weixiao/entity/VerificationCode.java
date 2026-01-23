package com.weixiao.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 验证码实体类
 * 对应数据库表 verification_code
 */
@Data
public class VerificationCode {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 邮箱地址
     */
    private String email;
    
    /**
     * 验证码
     */
    private String code;
    
    /**
     * 验证码类型 (REGISTER, LOGIN, RESET_PASSWORD)
     */
    private String type;
    
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 是否已使用 1:是 0:否
     */
    private Boolean isUsed;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 验证码类型常量
     */
    public static class Type {
        public static final String REGISTER = "REGISTER";
        public static final String LOGIN = "LOGIN";
        public static final String RESET_PASSWORD = "RESET_PASSWORD";
    }
    
    /**
     * 检查验证码是否过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
    
    /**
     * 检查验证码是否有效（未使用且未过期）
     */
    public boolean isValid() {
        return !isUsed && !isExpired();
    }
}