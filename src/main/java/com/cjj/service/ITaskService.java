package com.cjj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface ITaskService extends IService<Task> {

    /**
     *  根据员工ID未完成得到任务
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Task> getNotSuccessTaskByEmpId(Page<Task> page, Integer empId);

    /**
     *  根据员工ID已完成得到任务
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Task> getSuccessTaskByEmpId(Page<Task> page, Integer empId);

    /**
     *  根据员工ID得到所有任务
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Task> getAllTaskByEmpId(Page<Task> page, @Param("empId") Integer empId);

    /**
     *  根据发布者ID得到任务
     * @param page  分页对象
     * @param publisherId 发布者ID
     * @return      分页数据
     */
    Page<Task> getTaskByPublisherId(Page<Task> page, Integer publisherId);


    /**
     *  更新任务的状态
     * @param task  任务
     * @return      是否成功
     */
    boolean updateTaskStatus(Task task);

    /**
     *  得到当前页所有的任务
     * @return  当前页所有任务
     */
    Page<Task> selectAll(Page<Task> page);

    /**
     *  向任务表中添加员工
     * @param taskId    任务表ID
     * @param empIds    员工表ID
     * @return          是否成功
     */
    boolean addEmpToTask( int taskId, List<Integer> empIds);

}
