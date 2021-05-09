package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *      任务表实体类
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
public class Task extends Model<Task> {

    private static final long serialVersionUID = 1L;

    // 任务ID
    @TableId(value = "task_Id", type = IdType.AUTO)
    private Integer taskId;

    // 任务详情
    private String taskDetails;

    // 任务名称
    private String taskName;

    // 任务描述
    private String taskDescribe;

    // 发布者ID
    private Integer publisherId;

    // 任务是否成功
//    @TableField(exist = false)
    private Integer taskSuccess;

    // 发布的员工
    @TableField(exist = false)
    private String publisherName;


    // 员工ID
    @TableField(exist = false)
    private Integer empId;


//    /**
//     *  得到任务是否完成
//     * @return  任务是否完成
//     */
//    public String getTaskSuccess() {
//
//        return taskSuccess == 1? "已完成":taskSuccess==2?"未完成":"未办";
//    }

//    /**
//     *  得到当前任务的发布人
//     * @return
//     */
//    public String getPublisherName(){
//
//        return deptName;
//    }

    @Override
    protected Serializable pkVal() {
        return this.taskId;
    }

}
