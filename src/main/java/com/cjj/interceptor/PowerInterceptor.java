package com.cjj.interceptor;

import com.cjj.bean.Emp;
import com.cjj.bean.Resource;
import com.cjj.bean.Role;
import com.cjj.service.IResourceService;
import com.cjj.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 *  权限管理拦截器
 *
 *  用于拦截当前登陆用户
 *      判断是否用权限访问当前页面
 *
 *      如果没有，返回401
 *      如果有， 直接下一步
 *
 */
@Slf4j
@Component
public class PowerInterceptor implements HandlerInterceptor {

    // 资源服务层
    @Autowired
    private  IResourceService resourceService;

    // 角色服务层
    @Autowired
    private IRoleService roleService;

    /**
     *  前置处理
     * @param request   请求
     * @param response  想要
     * @param handler   处理
     * @return          是否放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 当前会话
        HttpSession session = request.getSession();

        // 如果没有登录, 就直接拦截
        if(session.getAttribute("user") != null) {

            // 员工
            Emp emp = (Emp) session.getAttribute("user");

            // 如果有员工登录
            if (emp != null) {

                // 得到当前用户的所有角色
                List<Role> roles = roleService.getRoleByEmpId(emp.getEmpId());

                // 将角色设置到当前用户的角色中
                emp.setRoles(roles);

                // 是否为管理员
                boolean isAdmin = false;

                // 遍历所有角色， 判断是否为管理员
                for (Role role : emp.getRoles()) {
                    if("管理员".equals(role.getRoleName())) {
                        isAdmin = true;
                        break;
                    }
                }

                // 如果是管理员， 直接放行
                if(isAdmin) {
                    return true;
                }


                // 当前员工的所有的资源集合
                List<Resource> resources = new ArrayList<>();

                // 得到当前用户的所有资源
                for (Role role : roles) {

                    // 得到当前角色
                    List<Resource> list = resourceService.getResourcesByRoleId(role.getRoleId());

                    // 给用户添加资源模块
                    for (Resource resource : list) {
                        // 判断是否存在集合中
                        if(!resources.contains(resource)) {
                            resources.add(resource);
                        }
                    }
                }

                // 所有的资源
                List<Resource> resourceList = resourceService.list();

                // 如果存在于当前所有的资源
                boolean isResource = false;

                // 遍历所有的资源
                for (Resource resource : resourceList) {

                    // 如果不是一级目录
                    if(resource.getResUrl() != null) {
                        // 如果当前请求的资源存在于资源中
                        if(request.getRequestURI().contains(resource.getResUrl())) {

                            // 如果没有删除
                            if(resource.getResDelete() == 1) {
                                isResource = true;
                                break;
                            } else {
                                // 报错404
                                response.sendError(404);
                                return false;
                            }

                        }
                    }
                }

                // 如果在所有的资源中
                if(isResource) {

                    // 遍历得到当前用户的所有一级资源
                    for (Resource resource : resources) {

                        // 遍历当前用户的所有二级资源
                        for (Resource resource1 : resource.getResourcePage()) {

                            // 如果当前请求的资源在用户资源当中
                            if(request.getRequestURI().equals("/"+resource1.getResUrl())) {
                                return true;
                            }
                        }
                    }

                    // 报错401
                    response.sendError(401);
                    return false;
                } else {

                    // 直接放行
                    return true;
                }

            } else {

                // 没有用户存在
                request.setAttribute("msg","请先登录");
                request.getRequestDispatcher("/").forward(request,response);
                return false;
            }
        }

        // 没有用户存在
        request.setAttribute("msg","请先登录");
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
