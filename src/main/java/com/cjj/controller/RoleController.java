package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.Resource;
import com.cjj.bean.Role;
import com.cjj.service.IResourceService;
import com.cjj.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@Slf4j
@RequestMapping("/role")
public class RoleController implements BaseController<Role> {

    // 角色服务层
    @Autowired
    private IRoleService roleService;

    // 分页
    private final Page<Role> page = new Page<>();


    @ResponseBody
    @GetMapping("/manage")
    public boolean isManager(HttpSession session) {

        return isManage(session) != 3;
    }

    @ResponseBody
    @GetMapping("/isManage")
    public boolean isManage1(HttpSession session) {

        return isManage(session) == 1;
    }


    /**
     *  得到角色
     *
     *      通常为管理员专属
     *
     * @param pageSize  当前页的大小
     * @param pageIndex 当前页数
     * @return          当前页的角色数据
     */
    @GetMapping("/role")
    @ResponseBody
    public Map<String, Object> getRole(@RequestParam("limit") Integer pageSize,
                                       @RequestParam("page") Integer pageIndex,
                                       @RequestParam(value = "likeName", required = false) String likeName) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);


        // 如果没有模糊查询
        if(likeName == null) {
            // 得到分页信息
            roleService.selectAll(page);


            // 有模糊查询
        } else {

            // 得到模糊查询
            roleService.getRoleLikeName(page, likeName);
        }



        return responseData(page);
    }


    /**
     *  编辑员工时使用
     *
     *  进入此处通常为管理员权限或者经理权限
     *      根据不同权限分配角色
     *          如果是管理员： 可以分配： 经理及以下所有资源
     *          如果是经理： 可以分配经理以下所有资源
     *
     *
     * @param session   当前会话
     * @return          当前编辑时的所有可用员工
     */
    @GetMapping("/editEmp")
    @ResponseBody
    public Map<String, Object> getRole(HttpSession session) {

        // 当前登陆的用户
        Emp emp = (Emp)session.getAttribute("user");

        // 查询数据
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();

        // 得到所有的角色
        List<Role> roles = roleService.getRoleByEmpId(emp.getEmpId());

        // 将所有角色赋值给当前用户
        emp.setRoles(roles);

        // 是否为管理员
        boolean isAdmin = false;

        // 判断是否为管理员
        for (Role role : roles) {

            // 如果是管理员
            if(role.getRoleName().equals("管理员")) {
                isAdmin = true;
                break;
            }
        }

        // 如果是管理员， 新建员工，不能给管理员权限
        if(isAdmin) {
            roleService.page(page, queryWrapper.eq("role_delete", 1)
                    .notIn("role_name", "管理员"));

            // 如果是非管理员， 不能给管理员权限和经理权限
        } else {
            roleService.page(page, queryWrapper.eq("role_delete", 1)
                    .notIn("role_name", "管理员", "部门经理"));
        }

        return responseData(page);
    }


    /**
     *  更新角色信息
     *
     *
     * @param role  更新的角色信息
     * @return
     */
    @PutMapping("/role")
    @ResponseBody
    public boolean updateEmp(Role role) {


        // 如果不是逻辑删除
        if(role.getRoleDelete() == null) {

            // 删除当前角色的所有资源
            roleService.deleteResForRole(role.getRoleId());
        }


        // 如果有资源ID， 表示需要更新当前角色的资源
        if(role.getRes() != null && role.getRes().size() != 0) {

            // 将获取的资源ID全部存入
            roleService.saveRoleToRes(role.getRes(), role.getRoleId());
        }

        return roleService.updateById(role);
    }


    /**
     *  插入角色信息
     * @param role  角色
     * @return      是否成功
     */
    @PostMapping("/role")
    @ResponseBody
    public boolean saveRole(Role role) {

        // 将当前角色插入到数据库中
        boolean success = roleService.save(role);

        // 如果成功
        if(success) {
            // 将选择的资源保存到对应的角色中
            if(role.getRes() != null && role.getRes().size() != 0) {
                roleService.saveRoleToRes(role.getRes(), role.getRoleId());
            }
        }

        return success;
    }

}

