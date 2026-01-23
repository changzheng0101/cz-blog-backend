package com.weixiao.service;

import com.weixiao.dto.UserLoginDTO;
import com.weixiao.dto.UserRegisterDTO;
import com.weixiao.dto.SendVerificationCodeDTO;

public interface AuthService {

    /**
     * 用户登录
     * @param loginDTO 登录参数
     * @return JWT Token
     */
    String login(UserLoginDTO loginDTO);

    /**
     * 用户注册
     * @param registerDTO 注册参数
     * @return 注册结果
     */
    String register(UserRegisterDTO registerDTO);

    /**
     * 发送验证码
     * @param sendDTO 发送验证码参数
     */
    void sendVerificationCode(SendVerificationCodeDTO sendDTO);
}