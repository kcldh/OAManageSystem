package com.cjj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.LeaveForm;
import com.cjj.bean.Reimburse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface IReimburseService extends IService<Reimburse> {


    /**
     *  查询所有
     * @param page  分页对象
     * @return      分页数据
     */
    Page<Reimburse> selectAll(Page<Reimburse> page);


    /**
     *  查询当前状态的所有请假
     * @param page  分页对象
     * @param success 请假状态
     */
    Page<Reimburse> selectAllByStatus(Page<Reimburse> page, Integer success);

    /**
     *  根据上级ID得到当前报销
     * @param page          分页对象
     * @param superiorId    上级ID
     */
    Page<Reimburse> getReimburseBySuperiorId(Page<Reimburse> page, Integer superiorId);


    /**
     *  根据上级ID以及当前报销状态
     *  得到当前报销信息
     * @param page          分页对象
     * @param superiorId    上级ID
     * @param success       报销状态 1 已完成， 2未完成 3 待审核
     */
    Page<Reimburse> getReimburseBySuperiorIdAndStatus(Page<Reimburse> page,
                                           @Param("superiorId") Integer superiorId,
                                           @Param("success") Integer success);

    /**
     *  得到统计柱形图的数据
     * @param page  分页对象
     * @return      分页数据
     */
    Page<Reimburse> reportStatistics(Page<Reimburse> page);

}
