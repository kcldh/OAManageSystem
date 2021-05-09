package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *      报销表实体类
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
public class Reimburse extends Model<Reimburse> {

    private static final long serialVersionUID = 1L;


    // 报销ID
    @TableId(type = IdType.AUTO, value = "reimburse_Id")
    private Integer reimburseId;

    // 报销日期
    private Date reimburseDate;

    // 报销员工
    private Integer empId;

    // 报销金额
    private Double reimburseMoney;

    // 报销是否成功
    private Integer reimburseSuccess;

    // 报销详情
    private String reimburseDetails;


    // 当前员工报销的金额
    @TableField(exist = false)
    private Double reimburseMoneys;

    // 当前请假的员工
    @TableField(exist = false)
    private Emp emp;


    /**
     *  得到当前请假的员工
     * @return  当前请假的员工名称
     */
    public String getEmpName() {

        // 如果有员工
        if(emp != null) {
            return emp.getEmpName();
        }

        return "";
    }


    public String getReimburseSuccess1() {
        return reimburseSuccess == 1 ? "已成功" : reimburseSuccess == 2 ? "未成功" : "待审核";
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }


}
