package com.cjj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Dept;
import com.cjj.bean.Emp;
import com.cjj.bean.Role;
import com.cjj.service.IDeptService;
import com.cjj.service.IEmpService;
import com.cjj.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  员工前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@RequestMapping("/emp")
@Slf4j
public class EmpController implements BaseController<Emp> {


    // 员工服务层
    @Autowired
    private IEmpService empService;

    // 部门服务层
    @Autowired
    private IDeptService deptService;

    // 角色服务层
    @Autowired
    private IRoleService roleService;

    // 当前对象的分页
    private final Page<Emp> page = new Page<>(1, 1);


    // 跳转到当前自己信息的页面
    @RequestMapping("/me")
    public String meMessage() {

        return "emp-me";
    }

    // 跳转到当前信息编辑页面
    @RequestMapping("/me-edit")
    public String meEditMessage() {

        return "emp-edit";
    }



    /**
     *  get请求所有员工
     * @param session   当前session会话
     * @param pageSize  当前页大小
     * @param pageIndex 当前页数
     * @return          所有员工
     */
    @ResponseBody
    @GetMapping("/emp")
    public Map<String, Object> empPage(HttpSession session,
                             @RequestParam("limit") Integer pageSize,
                             @RequestParam("page") Integer pageIndex,
                             @RequestParam(value = "deptId", required = false) Integer deptId,
                             @RequestParam(value = "likeName", required = false) String likeName) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        // 如果传入了部门ID表示是根据部门ID查询
        if(deptId != null && deptId > 0) {

            // 如果有模糊查询
            if(likeName == null) {
                // 根据部门ID得到部门员工信息
                // 通常为管理员用户查看部门信息
                // 当前查询不论是否逻辑删除都会查询
                empService.selectPageByDept(page, deptId);

            } else {
                // 只查询当前部门
                // 如果逻辑删除就不查询
                empService.selectPageLikeName(page, deptId, likeName);
            }
        } else {

            // 当前登陆的用户
            Emp emp = getUser(session);

            // 判断是否为管理员
            boolean isManage = userIsAdmin(emp);

            // 如果有模糊查询
            if(likeName == null) {
                // 如果是管理员
                if(isManage) {

                    // 查询所有
                    empService.selectAll(page);
                } else {

                    // 只查询当前部门
                    // 如果逻辑删除就不查询
                    empService.selectPage(page, emp.getDeptId());
                }
            } else {
                // 如果是管理员
                if(isManage) {

                    // 查询所有
                    empService.getEmpLikeName(page, likeName);
                } else {

                    // 只查询当前部门
                    // 如果逻辑删除就不查询
                    empService.selectPageLikeName(page, emp.getDeptId(), likeName);
                }
            }

        }

        // 响应的数据
        return responseData(page);
    }


    /**
     *  插入员工信息
     * @param emp   得到的员工数据
     * @return      是否成功
     */
    @PostMapping("/emp")
    @ResponseBody
    public boolean insertEmp(Emp emp, HttpSession session) {


        // 是否为管理员
        Integer isManage = isManage(session);


        // 如果不是管理员
        if(isManage == 2) {
            // 当前部门经理信息
            Emp manager = getUser(session);

            // 如果当前部门有经理
            if(manager != null) {
                emp.setSuperiorId(manager.getEmpId());
                emp.setDeptId(manager.getDeptId());
            }

        } else if(isManage == 1) {
            // 当前部门经理信息
            Emp manager = empService.getManager(emp.getDeptId());

            // 如果当前部门有经理
            if(manager != null) {
                emp.setSuperiorId(manager.getEmpId());
                emp.setDeptId(manager.getDeptId());
            }
        }

        // 执行插入
        boolean success = empService.save(emp);

        // 如果成功， 就将选择的所有角色保存到中间表
        if(success) {

            // 如果当前选择了角色
            if(emp.getRoleId() != null && emp.getRoleId().size() > 0) {
                empService.saveRoleToEmp(emp.getRoleId(), emp.getEmpId());
            }
        }

        return success;
    }



    @GetMapping("/resetPass")
    public String resetPass() {

        return "resetPass";
    }



    /**
     *  更新员工
     * @param emp   要更新的员工信息
     * @return      是否更新
     */
    @PutMapping("/emp")
    @ResponseBody
    public boolean updateEmp(Emp emp, HttpSession session) throws IOException {

        // 当前登陆的用户
        Emp user = getUser(session);

        int isManagement = isManage(session);


        // 得到当前部门信息
        Dept dept = deptService.getById(empService.getById(emp.getEmpId()).getDeptId());

        // 如果有这个部门
        if(dept != null) {

            // 判断当前部门是否被删除, 如果被删除就恢复
            if(dept.getDeptDelete() != 1) {

                // 将此部门恢复
                dept.setDeptDelete(1);
                deptService.updateById(dept);
            }
        }

        boolean success = empService.updateById(emp);
        // 如果是删除
        if(emp.getEmpDelete() == null) {
            if(emp.getEmpId() != null) {

                // 如果修改的是自己
                if(emp.getEmpId().equals(user.getEmpId())) {


                    // 如果是修改密码
                    if(emp.getEmpPass() != null) {
                        session.removeAttribute("user");
                    }

                    // 更新自己的数据
                    emp = empService.getById(emp.getEmpId());
                    emp.setRoles(user.getRoles());
                    emp.setDept(user.getDept());

                    session.setAttribute("user", emp);

                    // 不是修改自己
                } else {
                    // 如果是管理员
                    if(isManagement == 1) {

                        // 删除当前员工的所有角色
                        empService.deleteRoleFromEmp(emp.getEmpId());

                        // 如果有角色ID
                        if(emp.getRoleId() != null && emp.getRoleId().size() != 0){

                            // 像当前员工插入角色
                            empService.saveRoleToEmp(emp.getRoleId(), emp.getEmpId());
                        }
                    }
                }
            }
        }


        return success;
    }


}

