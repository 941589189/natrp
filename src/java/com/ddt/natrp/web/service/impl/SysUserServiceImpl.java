package com.ddt.natrp.web.service.impl;

import com.ddt.natrp.common.utils.PasswordUtils;
import com.ddt.natrp.web.domain.system.SysUser;
import com.ddt.natrp.web.mapper.SysUserMapper;
import com.ddt.natrp.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
@Lazy
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return sysUserMapper.selectUserList(user);
    }

    @Override
    //@Cacheable(value = "user" ,key = " 'userId' + '_' + #userId",unless="#result == null || #result.size() == 0")
    public SysUser selectUserById(Long userId) {
        log.info(userId + "已缓存");
        return sysUserMapper.selectUserById(userId);
    }

    @Override
   //@Cacheable(value = "user" ,key = " 'userName' + '_' + #userName",unless="#result == null")
    public SysUser selectUserByLoginName(String userName) {
        log.info("用户[" +userName + "]已被缓存");
        return sysUserMapper.selectUserByLoginName(userName);
    }

    @Override
    public int insertUser(SysUser user) {
        String salt = PasswordUtils.getSalt();
        //生成加盐密码
        String password = PasswordUtils.getMd5Password(user.getPassword(),user.getLoginName(),salt);
        user.setSalt(salt);
        //将user中未加盐密码更新
        user.setPassword(password);
        return sysUserMapper.insertUser(user);
    }

    @Override
    public int deleteUserById(Long userId) {
        return sysUserMapper.deleteUserById(userId);
    }

    @Override
    public int changeStatus(SysUser user) {
        //如果传入密码   则视为修改密码
        if(user.getPassword() != null){
            //生成盐
            String salt = PasswordUtils.getSalt();
            //生成加盐密码
            String password = PasswordUtils.getMd5Password(user.getPassword(),user.getLoginName(),salt);
            user.setSalt(salt);
            user.setPassword(password);
        }else {
            return -1;
        }
        return sysUserMapper.changeStatus(user);
    }
}
