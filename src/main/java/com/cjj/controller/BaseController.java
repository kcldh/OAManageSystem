package com.cjj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.Role;
import com.cjj.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  基本Controller
 *
 *
 * @param <E>
 */
public interface BaseController<E> {


    /**
     *  根据分页得到响应的数据
     * @param page  分页信息
     * @return      响应的数据
     */
    default Map<String, Object> responseData(Page<E> page) {

        // 返回的数据
        Map<String, Object> map = new HashMap<>();

        map.put("data", page.getRecords());
        map.put("count", page.getTotal());
        map.put("code", 0);

        return map;
    }


    /**
     *  是否为管理
     *      1表示管理员
     *      2表示经理
     *      3表示员工
     * @param session   当前会话
     * @return
     */
    default Integer isManage(HttpSession session) {

        int is = 3;

        Emp user = getUser(session);

        // 判断是否为管理员
        for (Role role : user.getRoles()) {
            if("管理员".equals(role.getRoleName())) {
                is = 1;
                break;
            } else if("部门经理".equals(role.getRoleName())) {
                is = 2;
                break;
            }
        }

        return is;
    }


    /**
     *  得到当前登陆的用户
     * @param session   当前会话
     * @return          当前登陆的用户
     */
    default Emp getUser(HttpSession session){
        Emp emp = (Emp) session.getAttribute("user");

        return emp;
    }


    /**
     *  判断当前登陆的用户是否为管理员
     *      如果是管理员
     *          返回 true
     *      否则
     *          返回 false
     *
     * @param emp   当前登陆的用户
     * @return      是否管理员
     */
    default boolean userIsAdmin(Emp emp) {

        boolean isManage = false;

        // 遍历当前员工的所有角色
        for (Role role : emp.getRoles()) {

            // 如果是管理员就直接退出循环
            if("管理员".equals(role.getRoleName())) {
                isManage = true;
                break;
            }
        }

        return isManage;
    }



}
