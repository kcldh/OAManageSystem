package com.cjj.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *  登陆拦截器
 *
 *
 *  判断是否登陆
 *
 *      如果未登陆跳到登陆页面
 *
 *      如果登陆了直接放行
 *
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    /**
     *  访问前处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 当前会话
        HttpSession session = request.getSession();

        // 如果有用户登陆直接放行
        if(session.getAttribute("user") != null) {
            return true;
        }

        // 直接跳转到登陆页面
        request.setAttribute("msg","请先登录");

        // 跳转到初始页面
        response.getWriter().append("<script>if(self != top){parent.window.location.href='/';}else{window.location.href='/';}</script>");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
