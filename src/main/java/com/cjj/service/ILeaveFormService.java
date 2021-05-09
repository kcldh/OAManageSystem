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
public interface ILeaveFormService extends IService<LeaveForm> {


    /**
     *  查询所有
     * @param page  分页对象
     * @return      分页数据
     */
    Page<LeaveForm> selectAll(Page<LeaveForm> page);


    /**
     *  查询当前状态的所有请假
     * @param page  分页对象
     * @param success 请假状态
     * @return      分页数据
     */
    Page<LeaveForm> selectAllByStatus(Page<LeaveForm> page, Integer success);


    /**
     *  根据上级ID得到当前请假
     * @param page          分页对象
     * @param superiorId    上级ID
     */
    void getLeaveBySuperiorId(Page<LeaveForm> page, Integer superiorId);


    /**
     *  根据上级ID以及当前请假状态
     *  得到当前请假信息
     * @param page          分页对象
     * @param superiorId    上级ID
     * @param success       报销状态 1 已完成， 2未完成 3 待审核
     */
    void getLeaveBySuperiorIdAndStatus(Page<LeaveForm> page, Integer superiorId, Integer success);


    /**
     *  得到统计柱形图的数据
     * @param page  分页对象
     * @return      分页数据
     */
    Page<LeaveForm> reportStatistics(Page<LeaveForm> page);

}
