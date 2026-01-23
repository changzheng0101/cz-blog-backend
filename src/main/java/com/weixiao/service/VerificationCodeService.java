package com.weixiao.service;

import com.weixiao.common.utils.VerificationCodeUtils;
import com.weixiao.entity.VerificationCode;
import com.weixiao.mapper.VerificationCodeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 验证码服务
 */
@Service
public class VerificationCodeService {
    
    private final VerificationCodeMapper verificationCodeMapper;
    private final MailService mailService;
    
    @Value("${verification.code.expire-minutes:10}")
    private int codeExpireMinutes;
    
    @Value("${verification.code.length:6}")
    private int codeLength;
    
    public VerificationCodeService(VerificationCodeMapper verificationCodeMapper, MailService mailService) {
        this.verificationCodeMapper = verificationCodeMapper;
        this.mailService = mailService;
    }
    
    /**
     * 生成并保存验证码
     * @param email 邮箱
     * @param type 验证码类型
     * @return 验证码
     */
    public String generateAndSaveCode(String email, String type) {
        // 生成验证码
        String code = VerificationCodeUtils.generateNumericCode(codeLength);
        
        // 创建验证码实体
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setCode(code);
        verificationCode.setType(type);
        verificationCode.setExpireTime(LocalDateTime.now().plusMinutes(codeExpireMinutes));
        verificationCode.setIsUsed(false);
        verificationCode.setCreateTime(LocalDateTime.now());
        verificationCode.setUpdateTime(LocalDateTime.now());
        
        // 保存验证码
        verificationCodeMapper.insert(verificationCode);
        
        return code;
    }
    
    /**
     * 发送注册验证码
     * @param email 邮箱
     */
    public void sendRegisterCode(String email) {
        String code = generateAndSaveCode(email, VerificationCode.Type.REGISTER);
        mailService.sendRegisterVerificationCode(email, code);
    }
    
    /**
     * 验证验证码
     * @param email 邮箱
     * @param code 验证码
     * @param type 验证码类型
     * @return 是否验证成功
     */
    public boolean validateCode(String email, String code, String type) {
        // 查找有效的验证码
        VerificationCode verificationCode = verificationCodeMapper.findValidByEmailAndType(email, type);
        
        if (verificationCode == null) {
            return false;
        }
        
        // 验证验证码
        boolean isValid = verificationCode.getCode().equals(code) && verificationCode.isValid();
        
        if (isValid) {
            // 标记验证码为已使用
            verificationCodeMapper.markAsUsed(verificationCode.getId());
        }
        
        return isValid;
    }
    
    /**
     * 检查验证码是否存在且有效
     * @param email 邮箱
     * @param type 验证码类型
     * @return 是否存在有效验证码
     */
    public boolean hasValidCode(String email, String type) {
        VerificationCode verificationCode = verificationCodeMapper.findValidByEmailAndType(email, type);
        return verificationCode != null && verificationCode.isValid();
    }
}