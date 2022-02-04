package com.manage.AllPlugin.config;


import com.manage.AllPlugin.config.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author qinsicheng
 * @Description 内容：添加拦截器到配置中，并设置拦截权限
 * @Date 06/12/2021 11:00
 */
@Configuration //说明配置类
// 当我们对web层进行配置时就必须实现WebMvcConfigurer
public class loginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //拦截器对谁进行使用
                .addPathPatterns("/**")  //拦截所有请求，包括静   态资源
                //拦截器不对谁使用
                .excludePathPatterns("/","/testLoginUserById","/login","/StuLogin","/studentPage","/stuClaRecord/**","/index","/regist","/css/**","/fonts/**","/images/**","/js/**");  //放行的请求
    }
}
