package com.cjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Resource;
import com.cjj.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  资源前端控制器
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Controller
@Slf4j
@RequestMapping("/resource")
public class ResourceController implements BaseController<Resource> {

    // 资源服务层
    @Autowired
    private IResourceService resourceService;

    // 分页信息
    private final Page<Resource> page = new Page<>();


    /**
     *  得到资源
     * @param pageSize  当前页的大小
     * @param pageIndex 当前页码
     * @return          当前页的所有资源
     */
    @GetMapping("/resource")
    @ResponseBody
    public Map<String, Object> getResources(@RequestParam("limit") Integer pageSize,
                                            @RequestParam("page") Integer pageIndex,
                                            @RequestParam(value = "likeName", required = false) String likeName) {

        // 设置第几页
        page.setCurrent(pageIndex);
        // 设置每页条数
        page.setSize(pageSize);

        // 如果没有模糊查询
        if(likeName == null) {

            // 得到分页信息
            resourceService.selectAll(page);

            // 有模糊查询
        } else {

            // 得到模糊查询
            resourceService.getResourceLikeName(page, likeName);
        }



        // 返回数据
        return responseData(page);

    }


    /**
     *  编辑角色
     *      新增角色和编辑角色时使用
     *      查询所有为逻辑删除的资源
     * @return  当前的资源数据
     */
    @GetMapping("/editRole")
    @ResponseBody
    public Map<String, Object> editRole() {

        // 得到分页信息
        resourceService.page(page, new QueryWrapper<Resource>()
                .eq("res_delete", "1")
                .notIn("res_name", "高级管理")
                .eq("parent_id", "0")
                .or()
                .isNull("parent_id"));

        // 响应数据
        return responseData(page);

    }


    /**
     *  更新资源
     *      如果资源是一级目录
     *      就将其设置null， 不更新
     *
     * @param resource      更新的资源信息
     * @return              是否更新成功
     */
    @PutMapping("/resource")
    @ResponseBody
    public boolean updateEmp(Resource resource) {

        // 如果是一级目录， 就把资源地址设置为null
        if("一级目录".equals(resource.getResUrl())) {
            resource.setResUrl(null);
        }

        // 更新资源
        return resourceService.updateById(resource);
    }




    /**
     *  新增资源
     * @param resource  新增的资源信息
     * @return          是否成功
     */
    @PostMapping("/resource")
    @ResponseBody
    public boolean saveRes(Resource resource) {

        log.info("/resource = {}", resource);
        return true;

        // 如果是一级目录， 就把资源地址设置为null
//        if("一级目录".equals(resource.getResUrl())) {
//            resource.setResUrl(null);
//        }
//
//
//        return resourceService.save(resource);
    }

}

