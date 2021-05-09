package com.cjj.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.Dept;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface IDeptService extends IService<Dept> {

    /**
     *  查询所有的部门
     * @param page  分页
     * @return      分页数据
     */
    Page<Dept> selectAll(Page<Dept> page);

}
