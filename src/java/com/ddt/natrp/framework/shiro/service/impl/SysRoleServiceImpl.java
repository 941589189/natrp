package com.ddt.natrp.framework.shiro.service.impl;

import cn.hutool.core.convert.Convert;
import com.ddt.natrp.common.exception.BusinessException;
import com.ddt.natrp.framework.shiro.domain.SysRole;
import com.ddt.natrp.framework.shiro.mapper.SysRoleMapper;
import com.ddt.natrp.framework.shiro.mapper.SysRoleMenuMapper;
import com.ddt.natrp.framework.shiro.mapper.SysUserRoleMapper;
import com.ddt.natrp.framework.shiro.service.SysRoleService;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lzx
 */
@Service(value = "SysRoleService")
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserRoleMapper sysUserRoleMapper, SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return sysRoleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    //@Cacheable(value = "role",key = " 'userId' + '_' + #userId",unless = "#result == null")
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = sysRoleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表 [admin, worker]
     */
    @Override
    //@Cacheable(value = "roleKey",key = " 'userId' + '_' + #userId")
    public Set<String> selectRoleKeys(Long userId) {
        log.info("用户" + userId + "角色权限已缓存");
        List<SysRole> perms = sysRoleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for(SysRole perm : perms){
            if(StringUtils.isNotNull(perm)){
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     * 需要坐aop切面
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {

        return sysRoleMapper.selectRoleList(new SysRole());
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return sysRoleMapper.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleById(Long roleId) {
        return sysRoleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @throws BusinessException 删除事务回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(String ids) {
        //字符串转Long数组
        Long[] roleIds = Convert.toLongArray(ids);
        for(Long roleId : roleIds){
            SysRole role = selectRoleById(roleId);
            //如果用户角色 关联表存在该RoleID
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        sysRoleMenuMapper.deleteRoleMenu(roleIds);
        return sysRoleMapper.deleteRoleByIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {
        return 0;
    }

    @Override
    public int updateRole(SysRole role) {
        return 0;
    }

    @Override
    public int authDataScope(SysRole role) {
        return 0;
    }

    /**
     * 查询该角色被 用户使用数量
     * @param roleId 角色ID
     * @return 1
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return sysUserRoleMapper.countUserRoleByRoleId(roleId);
    }

    @Override
    public int changeStatus(SysRole role) {
        return sysRoleMapper.changeStatus(role);
    }
}
