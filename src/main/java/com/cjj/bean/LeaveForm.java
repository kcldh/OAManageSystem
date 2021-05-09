package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *      请假表实体类
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
@TableName("LeaveForm")
public class LeaveForm extends Model<LeaveForm> {

    private static final long serialVersionUID = 1L;

    // 请假表ID
    @TableId(value = "leave_Id", type = IdType.AUTO)
    private Integer leaveId;

    // 请假日期
    private Date leaveDate;

    // 员工ID
    private Integer empId;

    // 请假详情
    private String leaveDetails;

    // 请假天数
    private Integer leaveDays;

    // 请假是否成功
    private Integer leaveSuccess;

    // 当前请假的总天数
    @TableField(exist = false)
    private Double leaveSumDay;

    // 当前请假的员工
    @TableField(exist = false)
    private Emp emp;


    /**
     *  得到当前请假的员工
     * @return  当前请假的员工名称
     */
    public String getEmpName() {

        if(emp != null) {
            return emp.getEmpName();
        }

        return "";
    }


    /**
     *  得到请假是否成功
     * @return  请假是否成功
     */
    public String getLeaveSuccess1() {
        return leaveSuccess == 1 ? "已成功" : leaveSuccess == 2 ? "未成功" : "待审核";
    }

    @Override
    protected Serializable pkVal() {
        return this.leaveId;
    }

}
