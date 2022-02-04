package com.manage.AllPlugin.config.interceptor;

import com.manage.Pojo.stu_login;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author qinsicheng
 * @Description 内容：拦截器  非登录状态无法进行访问其他页面
 * @Date 06/12/2021 10:54
 */
//配置拦截器拦截哪些请求 ，把这些配置放在容器中
    //拦截器要工作就要放在容器中
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        //登录检查逻辑
        HttpSession session = request.getSession();
        //从session检查是否有登录信息
        stu_login loginUser = (stu_login) session.getAttribute("loginUser");
        if (loginUser == null) {
            //阻止  并挑战到登录页面
            session.setAttribute("return_message", "请先进行登录");
            //请求重定向
            response.sendRedirect("/");
//            request.getRequestDispatcher("/").forward(request,response);  请求转发
            return false;
        } else {
            //放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
