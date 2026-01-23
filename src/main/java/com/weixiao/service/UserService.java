package com.weixiao.service;

import com.weixiao.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return 用户实体
     */
    User findByEmail(String email);
    
    /**
     * 创建新用户
     * @param user 用户实体
     * @return 创建的用户
     */
    User createUser(User user);
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);
}