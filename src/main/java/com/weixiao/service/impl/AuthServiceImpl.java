package com.weixiao.service.impl;

import com.weixiao.common.utils.JwtUtils;
import com.weixiao.dto.UserLoginDTO;
import com.weixiao.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String login(UserLoginDTO loginDTO) {
        // 1. 创建认证令牌
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // 2. 执行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 将认证信息存入 SecurityContext (虽然对于 JWT 无状态认证，这一步在登录接口中不是严格必须的，但对于保持上下文一致性有帮助)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 生成 JWT Token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails);
    }
}