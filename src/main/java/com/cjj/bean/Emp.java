package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *      员工表实体类
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
public class Emp extends Model<Emp> {

    private static final long serialVersionUID = 1L;

    // 员工ID
    @TableId(value = "emp_Id", type = IdType.AUTO)
    private Integer empId;

    // 员工名称
    private String empName;

    // 登陆名称
    private String loginName;

    // 员工密码
    private String empPass;

    // 邮箱
    private String email;

    // 电话号码
    private String number;

    // 地址
    private String address;

    // 员工性别
    private String sex;

    // 员工是否请假（1：未请假 ）
    private Integer empLeave;

    // 员工是否删除(1, 未删除)
    private Integer empDelete;

    // 上级ID
    private Integer superiorId;

    // 部门ID
    private Integer deptId;

    // 当前员工的所有角色
    @TableField(exist = false)
    private List<Role> roles;

    // 用来存储角色ID
    @TableField(exist = false)
    private List<Integer> roleId;

    // 当前员工的部门信息
    @TableField(exist = false)
    private Dept dept;


    /**
     *  得到部门的名称
     * @return  部门名称
     */
    public String getDeptName() {
        if(dept != null) {
            return dept.getDeptName();
        }

        return "暂无";
    }


    /**
     *  得到当前用户的状态
     * @return  当前用户状态
     */
    public String getStatus() {
        if(empLeave != null) {
            return empLeave == 1? "未请假":"请假";
        }

        return null;
    }


    /**
     *  得到当前员工所有角色的名称
     * @return  员工角色名称
     */
    public String getRolesName() {
        String str = "";

        // 如果有角色
        if(roles != null && roles.size() > 0) {

            // 遍历所有角色
            for (Role role : roles) {

                // 拼接角色名称
                str += role.getRoleName() + ",";
            }
        } else {
            str = "暂无";
        }

        // 如果有，就删除最后一个，
        // 如果没有，就直接返回str
        return str.contains(",") ?str.substring(0, str.lastIndexOf(",")):str;
    }


    /**
     *  比较方法
     * @param o 比较的值
     * @return  是否相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emp emp = (Emp) o;
        return emp.getEmpId() == this.empId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, empName, loginName, empPass, email, number, address, sex, empLeave, empDelete, superiorId, deptId, roles);
    }

    @Override
    protected Serializable pkVal() {
        return this.empId;
    }

}
