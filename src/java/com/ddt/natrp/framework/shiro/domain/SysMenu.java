package com.ddt.natrp.framework.shiro.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysMenu {
    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    private String orderNum;

    /** 菜单URL */
    private String url;

    /** 权限字符串 */
    private String permission;

    /** 菜单图标 */
    private String icon;

    /** */
    private String remake;

    /** 子菜单 */
    private List<SysMenu> children = new ArrayList<>();
}
