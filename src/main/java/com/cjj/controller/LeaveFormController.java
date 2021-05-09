package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.LeaveForm;
import com.cjj.bean.Reimburse;
import com.cjj.service.ILeaveFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  请假表前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@Slf4j
@RequestMapping("/leaveForm")
public class LeaveFormController implements BaseController<LeaveForm> {

    @Autowired
    private ILeaveFormService leaveFormService;

    // 分页对象
    private final Page<LeaveForm> page = new Page<>();


    /**
     *  get请求所有请假记录
     * @param session   当前session会话
     * @param pageSize  当前页大小
     * @param pageIndex 当前页数
     * @return          当前页所有请假信息
     */
    @ResponseBody
    @GetMapping("/leaveForm")
    public Map<String, Object> getLeaveForm(HttpSession session,
                                            @RequestParam("limit") Integer pageSize,
                                            @RequestParam("page") Integer pageIndex,
                                            @RequestParam(value = "success", required = false) Integer success) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        // 得到当前登陆的用户
        Emp emp = getUser(session);

        // 如果是用户查看需求
        if(success != null) {


            // 如果success小于5 表示是从查看审批那里查询
            if(success < 5) {

                if(success == 1) {
                    // 得到所有的请假
                    leaveFormService.page(page, new QueryWrapper<LeaveForm>()
                            .eq("emp_id", emp.getEmpId()));
                } else {
                    // 得到已成功的请假
                    leaveFormService.page(page, new QueryWrapper<LeaveForm>()
                            .eq("emp_id", emp.getEmpId())
                            .eq("leave_Success", success-1));
                }


                // 从管理页面查询
            } else {

                // 是否为管理员
                Integer integer = isManage(session);

                // 管理员
                if(integer == 1) {

                    // 查询所有
                    if(success == 5) {
                        // 查询所有
                        leaveFormService.selectAll(page);
                    } else {
                        // 根据请求得到的报销信息
                        leaveFormService.selectAllByStatus(page, success-5);
                    }

                    // 经理
                } else if(integer == 2) {

                    // 查询所有
                    if(success == 5) {
                        // 得到数据
                        leaveFormService.getLeaveBySuperiorId(page, getUser(session).getEmpId());
                    } else {
                        leaveFormService.getLeaveBySuperiorIdAndStatus(page,
                                getUser(session).getEmpId(), success-5);
                    }


                }

            }
        }

        return responseData(page);
    }


    /**
     *  新增请假
     * @param leaveForm 请假信息
     * @return          是否成功
     */
    @ResponseBody
    @PostMapping("/leaveForm")
    public boolean insertLeave(LeaveForm leaveForm, HttpSession session) {

        leaveForm.setEmpId(getUser(session).getEmpId());

        return leaveFormService.save(leaveForm);
    }


    /**
     *  更新请假
     * @param leaveForm 请假信息
     * @return          是否成功
     */
    @ResponseBody
    @PutMapping("/leaveForm")
    public boolean updateLeave(LeaveForm leaveForm) {

        return leaveFormService.updateById(leaveForm);
    }


    /**
     *  删除请假
     * @param leaveId 请假表ID
     * @return          是否成功
     */
    @ResponseBody
    @DeleteMapping("/leaveForm")
    public boolean deleteLeave(Integer leaveId) {

        return leaveFormService.removeById(leaveId);
    }



    /**
     *  柱形图
     * @return          柱形图数据
     */
    @ResponseBody
    @GetMapping("/barChart")
    public Map<String, Object> barChart() {


        // 得到柱形图数据
        leaveFormService.reportStatistics(page);


        return responseData(page);
    }

}



