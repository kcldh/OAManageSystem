package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Dept;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     *  根据部门ID得到部门信息
     *
     * @param deptId    部门ID
     * @return          当前ID的部门信息
     */
    Dept getDeptById(Integer deptId);


    /**
     *  查询所有的部门
     * @param page  分页
     * @return      分页数据
     */
    Page<Dept> selectAll(Page<Dept> page);

}
