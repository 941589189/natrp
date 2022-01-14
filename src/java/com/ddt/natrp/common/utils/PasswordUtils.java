package com.ddt.natrp.common.utils;


import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码加密的处理工具类 MD5
 **/
public class PasswordUtils {

    /**
     * 迭代次数
     */
    private static final int ITERATIONS = 6;
    /**
     * 盐值长度
     */
    private static final int SALT_NUMBER = 6;

    /**
     * 构造方法
     */
    private PasswordUtils() {
        throw new AssertionError();
    }

    /**
     * 证书凭证
     * 仅供内部函数调用  一般不外传
     * @param loginName 登录名
     * @param salt      盐值
     * @return
     */
    public static String getCredentialsSalt(String loginName, String salt) {
        return loginName + salt;
    }

    /**
     * 获得密码盐值
     *
     * @return
     */
    public static String getSalt() {
        return RandomUtils.getString(SALT_NUMBER);
    }

    /**
     * 字符串加密函数MD5实现
     * MD5+随机盐加密(loginName + salt)+散列(6)
     *
     * @param password  密码
     * @param loginName 用户名
     * @param salt      盐值
     * @return
     */
    public static String getMd5Password(String password, String loginName, String salt) {
        return new Md5Hash(password, getCredentialsSalt(loginName, salt), ITERATIONS).toString();
    }

}
