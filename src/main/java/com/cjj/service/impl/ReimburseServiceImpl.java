package com.cjj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.LeaveForm;
import com.cjj.bean.Reimburse;
import com.cjj.mapper.ReimburseMapper;
import com.cjj.service.IReimburseService;
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
public class ReimburseServiceImpl extends ServiceImpl<ReimburseMapper, Reimburse> implements IReimburseService {

    @Override
    public Page<Reimburse> selectAll(Page<Reimburse> page) {
        return baseMapper.selectAll(page);
    }

    @Override
    public Page<Reimburse> selectAllByStatus(Page<Reimburse> page, Integer success) {
        return baseMapper.selectAllByStatus(page, success);
    }

    @Override
    public Page<Reimburse> getReimburseBySuperiorId(Page<Reimburse> page, Integer superiorId) {
        return baseMapper.getReimburseBySuperiorId(page, superiorId);
    }

    @Override
    public Page<Reimburse> getReimburseBySuperiorIdAndStatus(Page<Reimburse> page, Integer superiorId, Integer success) {
        return baseMapper.getReimburseBySuperiorIdAndStatus(page, superiorId, success);
    }

    @Override
    public Page<Reimburse> reportStatistics(Page<Reimburse> page) {
        return baseMapper.reportStatistics(page);
    }
}
