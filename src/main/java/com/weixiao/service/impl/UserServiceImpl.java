package com.weixiao.service.impl;

import com.weixiao.entity.User;
import com.weixiao.mapper.UserMapper;
import com.weixiao.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
    
    @Override
    public User createUser(User user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 保存用户
        userMapper.insert(user);
        
        return user;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user != null;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        User user = userMapper.findByEmail(email);
        return user != null;
    }
}