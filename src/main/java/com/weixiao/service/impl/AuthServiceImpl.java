package com.weixiao.service.impl;

import com.weixiao.common.exception.GlobalException;
import com.weixiao.common.result.DataResponseCode;
import com.weixiao.common.utils.JwtUtils;
import com.weixiao.dto.SendVerificationCodeDTO;
import com.weixiao.dto.UserLoginDTO;
import com.weixiao.dto.UserRegisterDTO;
import com.weixiao.entity.User;
import com.weixiao.service.AuthService;
import com.weixiao.service.UserService;
import com.weixiao.service.VerificationCodeService;
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
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

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

    @Override
    public String register(UserRegisterDTO registerDTO) {
        // 1. 验证验证码
        boolean isValidCode = verificationCodeService.validateCode(
                registerDTO.getEmail(),
                registerDTO.getVerificationCode(),
                "REGISTER");
        
        if (!isValidCode) {
            throw new GlobalException(DataResponseCode.PARAM_ERROR, "验证码无效或已过期");
        }
        
        // 2. 检查用户名是否已存在
        if (userService.existsByUsername(registerDTO.getUsername())) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "用户名已存在");
        }
        
        // 3. 检查邮箱是否已注册
        if (userService.existsByEmail(registerDTO.getEmail())) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "邮箱已被注册");
        }
        
        // 4. 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        
        User createdUser = userService.createUser(user);
        
        // 5. 初始化用户之后进行额外操作

        
        return "success";
    }

    @Override
    public void sendVerificationCode(SendVerificationCodeDTO sendDTO) {
        // 检查邮箱是否已注册（仅注册类型需要检查）
        if ("REGISTER".equals(sendDTO.getType()) && userService.existsByEmail(sendDTO.getEmail())) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "邮箱已被注册");
        }
        
        // 发送验证码
        verificationCodeService.sendRegisterCode(sendDTO.getEmail());
    }
}