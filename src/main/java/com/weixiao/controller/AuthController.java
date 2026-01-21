package com.weixiao.controller;

import com.weixiao.common.result.DataResponse;
import com.weixiao.common.utils.JwtUtils;
import com.weixiao.dto.UserLoginDTO;
import com.weixiao.entity.User;
import com.weixiao.mapper.UserMapper;
import com.weixiao.service.AuthService;
import com.weixiao.vo.UserRolesPermissionsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public DataResponse<String> login(@RequestBody UserLoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return DataResponse.success(token);
    }

    @GetMapping("/roles-permissions")
    public DataResponse<UserRolesPermissionsVO> getRolesAndPermissions(@RequestHeader("Authorization") String authHeader) {
        try {
            // 从Authorization头中提取token
            String token = authHeader.replace("Bearer ", "");
            
            // 从token中提取用户名
            String username = jwtUtils.extractUsername(token);
            
            // 查询用户信息
            User user = userMapper.findByUsername(username);
            if (user == null) {
                return DataResponse.error("用户不存在");
            }
            
            // 查询用户角色和权限
            List<String> roles = userMapper.findRoleCodesByUserId(user.getId());
            List<String> permissions = userMapper.findPermissionsByUserId(user.getId());
            
            // 构建返回对象
            UserRolesPermissionsVO userRolesPermissions = new UserRolesPermissionsVO();
            userRolesPermissions.setUsername(username);
            userRolesPermissions.setRoles(roles);
            userRolesPermissions.setPermissions(permissions);
            
            return DataResponse.success(userRolesPermissions);
        } catch (Exception e) {
            return DataResponse.error("无效的token");
        }
    }
}