package com.weixiao.security.userdetails;

import com.weixiao.entity.User;
import com.weixiao.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 2. 查询用户角色和权限 (这里简化处理，将角色和权限都作为 Authority)
        List<String> roleCodes = userMapper.findRoleCodesByUserId(user.getId());
        List<String> permissionCodes = userMapper.findPermissionsByUserId(user.getId());

        // 3. 构建 Authority 列表
        List<SimpleGrantedAuthority> authorities = roleCodes.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 角色通常加 ROLE_ 前缀
                .collect(Collectors.toList());

        authorities.addAll(permissionCodes.stream()
                .map(SimpleGrantedAuthority::new)
                .toList());

        // 4. 返回 UserDetails 对象
        return new LoginUser(user, authorities);
    }
}