package com.ddt.natrp.web.controller.system;

import com.ddt.natrp.framework.shiro.domain.SysUserRole;
import com.ddt.natrp.framework.shiro.mapper.SysUserRoleMapper;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.service.SysUserService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/system/register")
@Api(value = "注册")
public class SysRegisterController extends BaseController {

    private final SysUserService sysUserService;

    private final SysUserRoleMapper sysUserRoleMapper;

    public SysRegisterController(SysUserService sysUserService, SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserService = sysUserService;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }


    @GetMapping("/main")
    public String openMain(){
        return "register";
    }

    /**
     *
     * @param username 昵称
     * @param loginName 登录名
     * @param password  密码
     * @return
     */
    @PostMapping(value = "/main")
    @ResponseBody
    @ApiOperation("注册")
    public AjaxResult main(@RequestParam("username") String username,
                           @RequestParam("loginname") String loginName,
                           @RequestParam("password") String password,
                           @RequestParam("usertype") String userType){
        SysUser userBase = sysUserService.selectUserByLoginName(loginName);
        if(userBase != null){
            return error("登录账号已存在");
        }else {
            SysUser user = new SysUser();
            user.setUserName(username);
            user.setLoginName(loginName);
            //默认为 注册用户
            user.setUserType(userType);
            user.setStatus("0");
            user.setPassword(password);
            try {
                sysUserService.insertUser(user);
                user = sysUserService.selectUserByLoginName(loginName);
                userSetRole(user.getUserId(),userType);
                return success("注册成功");
            }catch (Exception e){
                return error(e.getMessage());
            }
        }
    }

    /**
     * 给用户设置角色
     * @param userId 用户ID
     * @param userType 用户类型
     */
    public void userSetRole(Long userId,String userType){
        switch (userType) {
            case "00":
                break;
            default :
                List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(Role.VISITOR.value);
                userRoleList.add(sysUserRole);
                sysUserRoleMapper.batchUserRole(userRoleList);
                break;
        }
    }

    /**
     * Role枚举
     */
    public static enum Role{
        /**
         * 管理员
         */
        ADMIN(Long.valueOf("1")),
        /**
         * 工作人员
         */
        WORKER(Long.valueOf("2")),
        /**
         * 访客
         */
        VISITOR(Long.valueOf("3"));

        private final Long value;

        Role(Long value) {
            this.value = value;
        }
        public Long value(){
            return this.value;
        }
    }
}
