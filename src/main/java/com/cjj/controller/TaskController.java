package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.Role;
import com.cjj.bean.Task;
import com.cjj.service.IEmpService;
import com.cjj.service.IRoleService;
import com.cjj.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  任务前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@Slf4j
@RequestMapping("/task")
public class TaskController implements BaseController<Task>{

    // 任务表服务层
    @Autowired
    private ITaskService taskService;

    // 角色表服务层
    @Autowired
    private IRoleService roleService;

    // 员工表服务层
    @Autowired
    private IEmpService empService;

    // 分页对象
    private final Page<Task> page = new Page<>();


    /**
     *  get请求所有请假记录
     * @param session   当前session会话
     * @param pageSize  当前页大小
     * @param pageIndex 当前页数
     * @return          当前页所有请假信息
     */
    @ResponseBody
    @GetMapping("/task")
    public Map<String, Object> getLeaveForm(HttpSession session,
                                            @RequestParam("limit") Integer pageSize,
                                            @RequestParam("page") Integer pageIndex,
                                            @RequestParam(value = "success", required = false) Integer success) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        Emp emp = getUser(session);

        int isManagement = 1;

        // 得到当前用户的所有角色
        List<Role> roles = roleService.getRoleByEmpId(emp.getEmpId());

        // 判断是否为管理员
        for (Role role : roles) {
            if("管理员".equals(role.getRoleName())) {
                isManagement = 2;
                break;

                // 判断是否为经理
            } else if("部门经理".equals(role.getRoleName())) {
                isManagement = 3;
                break;
            }
        }

        // 如果不是管理员
        if(isManagement == 1) {

            if (success == 1) {
                taskService.getAllTaskByEmpId(page, emp.getEmpId());
            } else if(success == 2) {
                // 得到所有已完成的任务
                taskService.getSuccessTaskByEmpId(page, emp.getEmpId());
            } else {
                // 得到所有未完成的任务
                taskService.getNotSuccessTaskByEmpId(page, emp.getEmpId());
            }


            // 管理员查询
        } else if(isManagement == 2) {
            taskService.selectAll(page);

            // 经理查询
        } else {
            taskService.getTaskByPublisherId(page, emp.getEmpId());
        }

        return responseData(page);
    }


    /**
     *      更新任务
     * @param task      更新的任务对象
     * @param session   当前会话
     * @return          成功返回true
     */
    @PutMapping("/task")
    @ResponseBody
    public boolean updateTask(Task task, HttpSession session) {

        // 是否成功
        boolean success;

        // 修改任务状态
        if(task.getTaskSuccess() != null) {
            // 如果没有传入员工ID， 表示是当前用户自行修改
            if(task.getEmpId() == null) {
                // 得到当前登陆的用户
                Emp emp = getUser(session);

                // 设置当前员工ID
                task.setEmpId(emp.getEmpId());
            }

            // 执行修改
            success = taskService.updateTaskStatus(task);

            // 修改任务
        } else {

            // 更新任务
            success = taskService.updateById(task);
        }

        return success;
    }


    /**
     *  想任务表中插入任务
     * @param task      插入的任务
     * @param session   当前会话
     * @return          是否成功
     */
    @ResponseBody
    @PostMapping("/task")
    public boolean insertTask(Task task, HttpSession session) {

        // 当前登陆的用户
        Emp emp = getUser(session);

        // 设置发布者ID
        task.setPublisherId(emp.getEmpId());

        // 是否插入成功
        boolean success = taskService.save(task);

        if(isManage(session) == 1) {
            // 向任务表中插入员工的数据
            success = taskService.addEmpToTask(
                    task.getTaskId(),
                    // 得到不是管理的员工ID
                    empService.getNotManagerEmpId());
        }

        // 执行插入
        return success;
    }


    /**
     *  根据任务ID删除任务
     * @param taskId    任务ID
     * @return          是否成功
     */
    @DeleteMapping("/task")
    @ResponseBody
    public boolean deleteTask(Integer taskId) {

        // 根据任务ID删除任务
        return taskService.removeById(taskId);
    }

}

