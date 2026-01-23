package com.weixiao.mapper;

import com.weixiao.entity.VerificationCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VerificationCodeMapper {

    /**
     * 插入验证码
     */
    int insert(VerificationCode verificationCode);

    /**
     * 根据邮箱和类型查找未使用的验证码
     */
    VerificationCode findLatestByEmailAndType(@Param("email") String email, @Param("type") String type);

    /**
     * 根据邮箱和类型查找未过期的验证码
     */
    VerificationCode findValidByEmailAndType(@Param("email") String email, @Param("type") String type);

    /**
     * 标记验证码为已使用
     */
    int markAsUsed(@Param("id") Long id);

    /**
     * 清理过期的验证码
     */
    int deleteExpiredCodes(@Param("expireTime") LocalDateTime expireTime);

    /**
     * 根据邮箱查找所有未使用的验证码
     */
    List<VerificationCode> findUnusedByEmail(@Param("email") String email);
}