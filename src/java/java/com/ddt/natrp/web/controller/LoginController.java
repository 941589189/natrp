package com.ddt.natrp.web.controller;

import com.ddt.natrp.common.utils.PasswordUtils;
import com.ddt.natrp.common.utils.RSAUtil;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.service.SysUserService;
import com.example.demo.core.controller.BaseController;
import com.example.demo.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.subject.Subject;

@Controller
@Api(value = "登录页面")
@Slf4j
public class LoginController extends BaseController {

    private final SysUserService sysUserService;

    public LoginController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 登录时  获取公钥
     * @return AjaxResult
     */
    @GetMapping(value = "/key")
    @ResponseBody
    public AjaxResult keyRSA(){
        String publicKey = RSAUtil.generateBase64PublicKey();
        return success(publicKey);
    }

    @GetMapping(value = "/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "登录")
    @ResponseBody
    public AjaxResult ajaxLogin(@RequestParam("loginName") String username,
                                @RequestParam("password") String pwd){
        //rsa解密
        String password = RSAUtil.decryptBase64(pwd.trim());
        Subject subject = SecurityUtils.getSubject();
        SysUser user = sysUserService.selectUserByLoginName(username);
        String msg;
        if(user == null){
            msg = "账号不存在";
            return error(msg);
        }else {
            try {
                //取该用户数据库中的 盐
                String salt = user.getSalt();
                //对前端的数据进行加盐
                password = PasswordUtils.getMd5Password(password,username,salt);
                //用于传递账户密码的token
                UsernamePasswordToken utoken = new UsernamePasswordToken(username,password);
                subject.login(utoken);
                log.info("[" + username + "]" +"登录成功");
                return new AjaxResult(AjaxResult.Type.SUCCESS,username);
            }catch (AuthenticationException e){
                msg = "账号密码错误";
                log.info("对用户[" + username + "]进行登录验证..验证未通过{}");
                return error(msg);
            }
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
