package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     *  得到当前页所有的公告数据
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Notice> getAllNoticeByEmp(Page<Notice> page, @Param("empId") Integer empId);

    /**
     *  得到当前页未读的公告数据
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Notice> getNotReadNoticeByEmp(Page<Notice> page, @Param("empId") Integer empId);

    /**
     *  更新公告状态
     * @param notice    公告
     * @return          是否成功
     */
    boolean updateNoticeStatus(Notice notice);


    /**
     *  得到当前页已读的公告数据
     * @param page  分页对象
     * @param empId 员工ID
     * @return      分页数据
     */
    Page<Notice> getReadNoticeByEmp(Page<Notice> page, @Param("empId") Integer empId);


    /**
     *  查询所有发布的公告
     * @param page      分页
     * @return          分页数据
     */
    Page<Notice> selectAll(Page<Notice> page);

    /**
     *  查询当前员工发布的公告
     * @param page      分页
     * @return          分页数据
     */
    Page<Notice> selectNoticeByPublisherId(Page<Notice> page, @Param("publisherId") Integer publisherId);


    /**
     *  向公告表中添加员工
     * @param noticeId    任务表ID
     * @param empIds    员工表ID
     * @return          是否成功
     */
    boolean addEmpToNotice(@Param("noticeId") int noticeId, @Param("empIds") List<Integer> empIds);
}
