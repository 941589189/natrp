package com.ddt.natrp.framework.shiro.mapper;

import com.ddt.natrp.framework.shiro.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 —— 角色 关联表
 */

@Mapper
public interface SysUserRoleMapper {

    /**
     * 通过用户ID查询用户和角色关联
     *
     * @param userId 用户ID
     * @return 用户和角色关联列表
     */
    List<SysUserRole> selectUserRoleByUserId(Long userId);

    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据 userId
     * @return 结果
     */
    int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量取消 授权用户角色  把 该角色 对应的 用户 批量取消
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
