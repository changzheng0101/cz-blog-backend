package com.weixiao.service;

import com.weixiao.dto.UserLoginDTO;

public interface AuthService {

    /**
     * 用户登录
     * @param loginDTO 登录参数
     * @return JWT Token
     */
    String login(UserLoginDTO loginDTO);
}