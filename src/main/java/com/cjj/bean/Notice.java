package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *      公告表实体类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends Model<Notice> {

    private static final long serialVersionUID = 1L;

    // 公告ID
    @TableId(value = "notice_Id", type = IdType.AUTO)
    private Integer noticeId;

    // 公告描述
    private String noticeDescribe;

    // 公告名称
    private String noticeName;

    // 公告详情
    private String noticeDetails;

    // 发布者ID
    private Integer publisherId;

    // 是否已读
    private Integer markRead;

    // 发布者名称
    @TableField(exist = false)
    private String publisherName;

    // 员工ID
    @TableField(exist = false)
    private Integer empId;

    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }

}
