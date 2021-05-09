package com.cjj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjj.bean.Emp;
import com.cjj.bean.Resource;
import com.cjj.bean.Role;
import com.cjj.service.IDeptService;
import com.cjj.service.IEmpService;
import com.cjj.service.IResourceService;
import com.cjj.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 *  登录退出控制层
 */
@Controller
@Slf4j
public class LoginController {

    // 员工服务层
    @Autowired
    private IEmpService service;

    // 部门服务层
    @Autowired
    private IDeptService deptService;

    // 资源服务层
    @Autowired
    private IResourceService resourceService;

    // 角色服务层
    @Autowired
    private IRoleService roleService;


    @RequestMapping(value = {"/test"})
    public String test() {
        int exp = 1/0;
        return "login";
    }


    /**
     *  登录
     *  跳转到登陆页面
     * @return  登陆页面的名称
     */
    @RequestMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    /**
     *  是否成功
     *
     *      根据用户输入的用户名和密码判断是否登陆成功
     *          如果登陆成功
     *              返回  成功的HTML页面地址
     *          否则
     *              返回  登陆页面的地址
     *
     * @param username  用户名
     * @param password  密码
     * @param model     模型
     * @param session   会话
     * @return          页面地址
     */
    @RequestMapping(value = {"/index.html"})
    public String success(String username, String password, Model model, HttpSession session) {
        String s = "login";

        // 用户输入的用户名和密码
        if(username!=null && password!=null) {

            // 得到当前用户的信息
            Emp one = service.getOne(new QueryWrapper<Emp>()
                    .eq("login_name", username)
                    .eq("emp_pass", password)
                    .eq("emp_delete","1"));


            // 如果有这个员工
            if (one != null) {

                // 所有的资源集合
                List<Resource> resources = new ArrayList<>();

                // 管理员
                if(one.getDeptId() == null) {
                    resources = resourceService.getResources();

                    // 将所有的资源添加到属性中
                    model.addAttribute("resources", resources);

                    // 成功的页面地址
                    s = "index1";

                    // 将用户存入会话
                    session.setAttribute("user", one);

                    // 不是管理员
                } else {

                    // 得到当前登陆用户的部门信息
                    one.setDept(deptService.getById(one.getDeptId()));

                    // 如果当前部门没有删除
                    if(one.getDept().getDeptDelete() == 1) {

                        // 得到这个用户所有的角色
                        List<Role> roles = roleService.getRoleByEmpId(one.getEmpId());
                        one.setRoles(roles);

                        for (Role role : roles) {
                            List<Resource> list;

                            // 得到当前角色的所有资源
                            list = resourceService.getResourcesByRoleId(role.getRoleId());

                            // 给用户添加资源模块
                            for (Resource resource : list) {

                                // 判断是否存在集合中
                                if(!resources.contains(resource)) {
                                    resources.add(resource);
                                }
                            }
                        }

                        // 将所有的资源添加到属性中
                        model.addAttribute("resources", resources);

                        // 成功的页面地址
                        s = "index1";

                        // 将用户存入会话
                        session.setAttribute("user", one);
                    } else {
                        // 将失败信息放入数据
                        model.addAttribute("msg", "当前部门已被封禁， 请联系管理员");

                    }

                }

            } else {

                // 将失败信息放入数据
                model.addAttribute("msg", "用户名或密码错误");
            }

        } else {

            // 如果没有用户登陆
            if(session.getAttribute("user") == null) {

                // 错误信息
                model.addAttribute("msg", "请先登陆");
            } else {

                // 返回登陆成功页面
                s = "index1";
            }

        }

        return s;
    }


    /**
     *  退出
     * @param session   当前会话
     * @return
     */
    @RequestMapping("/signOut")
    public String signOut(HttpSession session) {

        // 删除user信息
        session.removeAttribute("user");

        // 返回登陆页面
        return "login";
    }


}
