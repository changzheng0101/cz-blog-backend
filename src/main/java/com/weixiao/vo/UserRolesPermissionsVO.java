package com.weixiao.vo;

import lombok.Data;
import java.util.List;

@Data
public class UserRolesPermissionsVO {
    private Long userId;
    private String username;
    private List<String> roles;
    private List<String> permissions;
}