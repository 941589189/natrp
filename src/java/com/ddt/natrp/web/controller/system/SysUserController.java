package com.ddt.natrp.web.controller.system;

import com.ddt.natrp.common.utils.PasswordUtils;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.service.SysUserService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/system/user")
@Slf4j
public class SysUserController extends BaseController {

    private final SysUserService userService;

    public SysUserController(SysUserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/password")
    public ModelAndView openPassword(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userPassword");
        mv.addObject("user",user);
        return mv;
    }

    /**
     * 修改用户密码
     * @param password 密码
     * @return AjaxResult
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public AjaxResult changePassword(@RequestParam("password") String password){
        //取出当前用户数据
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //判断新密码与旧密码是否相同
        if(PasswordUtils.getMd5Password(password,user.getLoginName(),user.getSalt()).equals(user.getPassword())){
            return error("新密码不得与旧密码相同");
        }
        SysUser userNew = new SysUser();
        //在新建用户中设置用户Id
        userNew.setUserId(user.getUserId());
        //新建用户设置用户登录名
        userNew.setLoginName(user.getLoginName());
        //设置新密码
        userNew.setPassword(password);
        try{
            userService.changeStatus(userNew);
            log.info("用户[" + user.getUserName() + "]密码修改成功");
            return success("密码修改成功");
        }catch (Exception e){
            log.info("用户[" + user.getUserName() + "]密码修改失败");
            return error("密码修改失败");
        }
    }
}
