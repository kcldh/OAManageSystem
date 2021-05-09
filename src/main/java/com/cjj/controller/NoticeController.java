package com.cjj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.Notice;
import com.cjj.bean.Role;
import com.cjj.service.IEmpService;
import com.cjj.service.INoticeService;
import com.cjj.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *      通知前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@RequestMapping("/notice")
public class NoticeController implements BaseController<Notice> {

    // 角色服务层
    @Autowired
    private IEmpService empService;

    // 公告服务层
    @Autowired
    private INoticeService noticeService;

    // 分页
    private final Page<Notice> page = new Page<>();


    /**
     *  get请求所有请假记录
     * @param session   当前session会话
     * @param pageSize  当前页大小
     * @param pageIndex 当前页数
     * @return          当前页所有请假信息
     */
    @ResponseBody
    @GetMapping("/notice")
    public Map<String, Object> getLeaveForm(HttpSession session,
                                            @RequestParam("limit") Integer pageSize,
                                            @RequestParam("page") Integer pageIndex,
                                            @RequestParam(value = "success", required = false) Integer success) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        // 当前登陆的员工
        Emp emp = getUser(session);

        // 表示员工或者经理查询
        if(success != null ){

            // 查询所有
            if (success == 1) {
                noticeService.getAllNoticeByEmp(page, emp.getEmpId());
            } else if(success == 2) {
                // 得到所有已完成的任务
                noticeService.getReadNoticeByEmp(page, emp.getEmpId());
            } else {
                // 得到所有未完成的任务
                noticeService.getNotReadNoticeByEmp(page, emp.getEmpId());
            }

            // 如果没有查询已完成和未完成表示当前在管理页面查询
        } else {

            // 是否为管理员
            int isManagement = isManage(session);

            // 如果不是管理员
            if(isManagement != 1) {

                // 得到发布者的ID
                noticeService.selectNoticeByPublisherId(page, emp.getEmpId());

                // 管理员查询
            } else {
                noticeService.selectAll(page);
            }
        }

        return responseData(page);
    }


    /**
     *      更新任务
     * @param notice      更新的任务对象
     * @param session   当前会话
     * @return          成功返回true
     */
    @PutMapping("/notice")
    @ResponseBody
    public boolean updateNotice(Notice notice, HttpSession session) {

        // 是否成功
        boolean success;

        // 如果是修改任务状态
        if(notice.getMarkRead() != null) {
            // 如果没有传入员工ID， 表示是当前用户自行修改
            if(notice.getPublisherId() == null) {
                // 得到当前登陆的用户
                Emp emp = getUser(session);

                // 设置当前员工ID
                notice.setEmpId(emp.getEmpId());
            }

            // 执行修改
            success = noticeService.updateNoticeStatus(notice);

            // 修改任务
        } else {

            // 更新任务
            success = noticeService.updateById(notice);
        }

        return success;
    }


    /**
     *  想任务表中插入任务
     * @param notice      插入的任务
     * @param session   当前会话
     * @return          是否成功
     */
    @ResponseBody
    @PostMapping("/notice")
    public boolean insertNotice(Notice notice, HttpSession session) {

        // 当前登陆的用户
        Emp emp = getUser(session);

        // 设置发布者ID
        notice.setPublisherId(emp.getEmpId());

        // 是否插入成功
        boolean success = noticeService.save(notice);

        // 判断是否为管理员
        Integer manage = isManage(session);


        // 如果是管理员， 就向所有的员工添加公告
        if(manage == 1) {
            noticeService.addEmpToNotice(
                    notice.getNoticeId(),
                    empService.getNotManagerId());
        }

        // 执行插入
        return success;
    }


    /**
     *  根据任务ID删除任务
     * @param noticeId    任务ID
     * @return          是否成功
     */
    @DeleteMapping("/notice")
    @ResponseBody
    public boolean deleteNotice(Integer noticeId) {

        // 根据任务ID删除任务
        return noticeService.removeById(noticeId);
    }


}

