package com.ddt.natrp.framework.shiro.realm;

import com.ddt.natrp.common.utils.ShiroUtils;
import com.ddt.natrp.framework.shiro.service.SysMenuService;
import com.ddt.natrp.framework.shiro.service.SysRoleService;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private SysUserService sysUserService;

    @Lazy
    @Autowired
    private SysMenuService sysMenuService;

    @Lazy
    @Autowired
    private SysRoleService sysRoleService;

    private static final Long USERID_ADMIN = 1L;

    /** 授权认证 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles;
        // 功能列表
        Set<String> menus;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(user.getUserId().longValue() == USERID_ADMIN.longValue()){
            info.addRole("admin");
            info.addRole("worker");
            info.addStringPermission("*:*:*");
        }else {
            roles = sysRoleService.selectRoleKeys(user.getUserId());
            menus = sysMenuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /** 登录认证 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken =(UsernamePasswordToken) token;

        String username = utoken.getUsername();
        if(("").equals(username) || null == username){
            throw new AuthenticationException("账号不得为空");
        }
        SysUser user;
        user = sysUserService.selectUserByLoginName(username);
        if(user != null){
            //参数依次为  user实体  数据库中password  此realm名称
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }else {
            throw new AuthenticationException("账号密码错误");
        }
    }
}
