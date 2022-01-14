package com.ddt.natrp.web.domain.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户对象
 */
@Data
public class SysUser implements Serializable {

    /** 用户ID */
    private Long userId;

    /** 登录名称 */
    private String loginName;

    /** 用户名称 */
    private String userName;

    /** 密码 */
    private String password;

    /** 用户类型 */
    private String userType;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public SysUser(){
        super();
    }
}
