package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
import com.cjj.bean.LeaveForm;
import com.cjj.bean.Reimburse;
import com.cjj.service.IReimburseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/reimburse")
public class ReimburseController implements BaseController<Reimburse>{

    @Autowired
    private IReimburseService reimburseService;

    private final Page<Reimburse> page = new Page<>();

    /**
     *  get请求所有报销记录
     * @param session   当前session会话
     * @param pageSize  当前页大小
     * @param pageIndex 当前页数
     * @return          当前页所有报销信息
     */
    @ResponseBody
    @GetMapping("/reimburse")
    public Map<String, Object> getLeaveForm(HttpSession session,
                                            @RequestParam("limit") Integer pageSize,
                                            @RequestParam("page") Integer pageIndex,
                                            @RequestParam(value = "success", required = false) Integer success) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);


        // 如果是用户查看需求
        if(success != null) {


            // 如果success小于5 表示是从查看任务那里查询
            if(success < 5) {

                if(success == 1) {
                    // 得到当前登陆用户当前页所有的报销
                    reimburseService.page(page, new QueryWrapper<Reimburse>()
                            .eq("emp_id", getUser(session).getEmpId()));

                    // 表示是查询
                } else {
                    // 根据请求得到的报销信息
                    reimburseService.page(page, new QueryWrapper<Reimburse>()
                            .eq("emp_id", getUser(session).getEmpId())
                            .eq("reimburse_Success", success-1));

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
                        reimburseService.selectAll(page);
                    } else {
                        // 根据请求得到的报销信息
                        reimburseService.selectAllByStatus(page, success-5);
                    }


                    // 经理
                } else if(integer == 2) {

                    // 查询所有
                    if(success == 5) {
                        // 得到数据
                        reimburseService.getReimburseBySuperiorId(page, getUser(session).getEmpId());
                    } else {
                        reimburseService.getReimburseBySuperiorIdAndStatus(page,
                                getUser(session).getEmpId(), success-5);
                    }


                }

            }
        }

        return responseData(page);
    }


    /**
     *  新增报销
     * @param reimburse 报销信息
     * @return          是否成功
     */
    @ResponseBody
    @PostMapping("/reimburse")
    public boolean insertLeave(Reimburse reimburse, HttpSession session) {

        reimburse.setEmpId(getUser(session).getEmpId());

        return reimburseService.save(reimburse);
    }


    /**
     *  更新当前报销的状态
     * @param r 报销对象
     * @return  是否成功
     */
    @PutMapping("/reimburse")
    @ResponseBody
    public boolean updateLeave(Reimburse r){

        return reimburseService.updateById(r);
    }


    /**
     *  删除报销
     * @param reimburseId 报销表ID
     * @return              是否成功
     */
    @ResponseBody
    @DeleteMapping("/reimburse")
    public boolean deleteLeave(Integer reimburseId) {

        return reimburseService.removeById(reimburseId);
    }

    /**
     *  柱形图
     * @return          柱形图数据
     */
    @ResponseBody
    @GetMapping("/barChart")
    public Map<String, Object> barChart() {


        // 得到柱形图数据
        reimburseService.reportStatistics(page);


        return responseData(page);
    }

}

