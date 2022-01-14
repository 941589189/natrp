package com.ddt.natrp.config.resouce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * 静态资源路径重定义
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** 静态资源 */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
    /** 浏览器资源*/
    private static final String BROWSER_RESOURCE_LOCATIONS = "/webjars/**";
    /**全部资源*/
    private static final String ALL_RESOURCE_LOCATIONS = "/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern(BROWSER_RESOURCE_LOCATIONS)) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern(ALL_RESOURCE_LOCATIONS)) {
            registry.addResourceHandler(ALL_RESOURCE_LOCATIONS).addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }
    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties(){
        System.setProperty("druid.mysql.usePingMethod","false");
    }


}