package com.cjj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.LeaveForm;
import com.cjj.bean.Reimburse;
import com.cjj.mapper.LeaveFormMapper;
import com.cjj.service.ILeaveFormService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Service
public class LeaveFormServiceImpl extends ServiceImpl<LeaveFormMapper, LeaveForm> implements ILeaveFormService {

    @Override
    public Page<LeaveForm> selectAll(Page<LeaveForm> page) {

        return baseMapper.selectAll(page);
    }

    @Override
    public Page<LeaveForm> selectAllByStatus(Page<LeaveForm> page, Integer success) {

        return baseMapper.selectAllByStatus(page, success);
    }

    @Override
    public void getLeaveBySuperiorId(Page<LeaveForm> page, Integer superiorId) {
        baseMapper.getLeaveBySuperiorId(page, superiorId);
    }

    @Override
    public void getLeaveBySuperiorIdAndStatus(Page<LeaveForm> page, Integer superiorId, Integer success) {
        baseMapper.getLeaveBySuperiorIdAndStatus(page, superiorId, success);
    }

    @Override
    public Page<LeaveForm> reportStatistics(Page<LeaveForm> page) {
        return baseMapper.reportStatistics(page);
    }
}
