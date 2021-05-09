package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Dept;
import com.cjj.bean.Emp;
import com.cjj.service.IDeptService;
import com.cjj.service.IEmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  部门前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@Slf4j
@RequestMapping("/dept")
public class DeptController implements BaseController<Dept> {

    // 部门服务层
    @Autowired
    private IDeptService deptService;

    // 员工服务层
    @Autowired
    private IEmpService empService;

    // 分页对象
    private final Page<Dept> page = new Page<>();


    /**
     *  得到部门
     *      当前得到的部门 为：未逻辑删除的部门
     *      适用于新建员工时分配部门
     * @return  未逻辑删除的部门
     */
    @GetMapping("/editDept")
    @ResponseBody
    public Map<String, Object> getDept(HttpSession session) {

        Emp emp = getUser(session);

        if(emp.getDeptId() == null) {
            // 得到所有未逻辑删除的部门
            deptService.page(page, new QueryWrapper<Dept>()
                    .eq("dept_delete", 1));
        }

        // 返回数据
        return responseData(page);
    }

    /**
     *  get请求所有的部门信息
     *     访问此数据为管理员权限
     * @param pageSize  分页大小
     * @param pageIndex 当前页数
     * @return          当前页数据
     */
    @GetMapping("/dept")
    @ResponseBody
    public Map<String, Object> getDepts(@RequestParam("limit") Integer pageSize,
                                        @RequestParam("page") Integer pageIndex) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        // 得到分页数据
        deptService.selectAll(page);

        log.info("data={}",responseData(page));

        //  返回数据
        return responseData(page);
    }


    /**
     *  更新员工
     * @param dept   要更新的员工信息
     * @return      是否更新
     */
    @PutMapping("/dept")
    @ResponseBody
    public boolean updateEmp(Dept dept) {

        // 返回是否禁用
        return deptService.updateById(dept);
    }


    /**
     *  新增部门信息
     * @param dept  新增的部门
     * @return      是否成功
     */
    @PostMapping("/dept")
    @ResponseBody
    public boolean insertDept(Dept dept) {

        // 保存
        return deptService.save(dept);
    }

}

