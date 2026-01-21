package com.weixiao.mapper;

import com.weixiao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据用户ID查询角色编码列表
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> findRoleCodesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询权限编码列表
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> findPermissionsByUserId(@Param("userId") Long userId);
}