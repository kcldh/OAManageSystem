package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.LeaveForm;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface LeaveFormMapper extends BaseMapper<LeaveForm> {

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
    Page<LeaveForm> selectAllByStatus(Page<LeaveForm> page, @Param("success") Integer success);

    /**
     *  根据上级ID得到当前请假
     * @param page          分页对象
     * @param superiorId    上级ID
     * @return              分页数据
     */
    Page<LeaveForm> getLeaveBySuperiorId(Page<LeaveForm> page, @Param("superiorId") Integer superiorId);


    /**
     *  根据上级ID以及当前请假状态
     *  得到当前请假信息
     * @param page          分页对象
     * @param superiorId    上级ID
     * @param success       报销状态 1 已完成， 2未完成 3 待审核
     * @return              分页数据
     */
    Page<LeaveForm> getLeaveBySuperiorIdAndStatus(Page<LeaveForm> page,
                                                      @Param("superiorId") Integer superiorId,
                                                      @Param("success") Integer success);


    /**
     *  得到统计柱形图的数据
     * @param page  分页对象
     * @return      分页数据
     */
    Page<LeaveForm> reportStatistics(Page<LeaveForm> page);

}
