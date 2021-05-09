package com.cjj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Task;
import com.cjj.mapper.TaskMapper;
import com.cjj.service.ITaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Override
    public Page<Task> getNotSuccessTaskByEmpId(Page<Task> page, Integer empId) {
        return baseMapper.getNotSuccessTaskByEmpId(page, empId);
    }

    @Override
    public Page<Task> getSuccessTaskByEmpId(Page<Task> page, Integer empId) {
        return baseMapper.getSuccessTaskByEmpId(page, empId);
    }

    @Override
    public Page<Task> getAllTaskByEmpId(Page<Task> page, Integer empId) {
        return baseMapper.getAllTaskByEmpId(page, empId);
    }

    @Override
    public Page<Task> getTaskByPublisherId(Page<Task> page, Integer publisherId) {
        return baseMapper.getTaskByPublisherId(page, publisherId);
    }

    @Override
    public boolean updateTaskStatus(Task task) {
        return baseMapper.updateTaskStatus(task);
    }

    @Override
    public Page<Task> selectAll(Page<Task> page) {
        return baseMapper.selectAll(page);
    }

    @Override
    public boolean addEmpToTask(int taskId, List<Integer> empIds) {
        return baseMapper.addEmpToTask(taskId, empIds);
    }
}
