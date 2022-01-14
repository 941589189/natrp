package com.ddt.natrp.framework;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lzx
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
//开启spring注解aop配置的支持
@EnableAspectJAutoProxy(exposeProxy = true)
//开启Swagger
@EnableSwagger2
//开启异步多线程
@EnableAsync
//开启缓存
//@EnableCaching

public class ApplicationConfig
{

}