package com.cjj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Notice;
import com.cjj.mapper.NoticeMapper;
import com.cjj.service.INoticeService;
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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public Page<Notice> getAllNoticeByEmp(Page<Notice> page, Integer empId) {
        return baseMapper.getAllNoticeByEmp(page, empId);
    }

    @Override
    public Page<Notice> getNotReadNoticeByEmp(Page<Notice> page, Integer empId) {
        return baseMapper.getNotReadNoticeByEmp(page, empId);
    }

    @Override
    public boolean updateNoticeStatus(Notice notice) {
        return baseMapper.updateNoticeStatus(notice);
    }

    @Override
    public Page<Notice> getReadNoticeByEmp(Page<Notice> page, Integer empId) {
        return baseMapper.getReadNoticeByEmp(page, empId);
    }

    @Override
    public Page<Notice> selectAll(Page<Notice> page) {
        return baseMapper.selectAll(page);
    }

    @Override
    public Page<Notice> selectNoticeByPublisherId(Page<Notice> page, Integer publisherId) {
        return baseMapper.selectNoticeByPublisherId(page, publisherId);
    }

    @Override
    public boolean addEmpToNotice(int noticeId, List<Integer> empIds) {
        return baseMapper.addEmpToNotice(noticeId, empIds);
    }
}
