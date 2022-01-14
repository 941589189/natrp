package com.ddt.natrp.framework.shiro.service.impl;

import com.ddt.natrp.framework.shiro.domain.SysMenu;
import com.ddt.natrp.framework.shiro.domain.SysRole;
import com.ddt.natrp.framework.shiro.mapper.SysMenuMapper;
import com.ddt.natrp.framework.shiro.service.SysMenuService;
import com.ddt.natrp.web.domain.system.SysUser;
import com.example.demo.core.domain.Ztree;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    private static final Long USERID_ADMIN = 1L;

    private final SysMenuMapper sysMenuMapper;

    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper){
        this.sysMenuMapper = sysMenuMapper;
    }
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menus;
        // 管理员显示所有菜单信息
        if (user.getUserId().longValue() == USERID_ADMIN.longValue())
        {
            menus = sysMenuMapper.selectMenuNormalAll();
        }
        else
        {
            menus = sysMenuMapper.selectMenusByUserId(user.getUserId());
        }
        return menus;
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        return null;
    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll(Long userId) {
        List<SysMenu> menuList;
        if (userId.longValue() == USERID_ADMIN.longValue())
        {
            menuList = sysMenuMapper.selectMenuAll();
        }
        else
        {
            menuList = sysMenuMapper.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表 [system:user, system:notice:open, system:user:password, reportin, report:export, system:notice]
     */
    @Override
    //@Cacheable(value = "params",key = " 'userId' + '_' + #userId")
    public Set<String> selectPermsByUserId(Long userId) {
        log.info("用户" + userId + "菜单权限已缓存");
        List<String> perms = sysMenuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId) {
        return null;
    }

    @Override
    public List<Ztree> menuTreeData(Long userId) {
        return null;
    }

    @Override
    public Map<String, String> selectPermsAll(Long userId) {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<SysMenu> permissions = selectMenuAll(userId);
        if (StringUtils.isNotEmpty(permissions))
        {
            for (SysMenu menu : permissions)
            {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPermission()));
            }
        }
        return section;
    }

    /**
     * 根据菜单ID删除
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMenuById(Long menuId) {
        return sysMenuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return sysMenuMapper.selectMenuById(menuId);
    }

    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return 0;
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return 0;
    }

    @Override
    public int insertMenu(SysMenu menu) {
        return 0;
    }

    @Override
    public int updateMenu(SysMenu menu) {
        return 0;
    }

    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        return null;
    }
}
