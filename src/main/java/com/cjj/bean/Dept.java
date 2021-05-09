package com.cjj.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * <p>
 *      部门表实体类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public class Dept extends Model<Dept> {

    // 部门ID
    @TableId(value = "dept_Id", type = IdType.AUTO)
    private Integer deptId;

    // 部门名称
    private String deptName;

    // 部门描述
    private String deptDetails;

    // 部门是否删除（0， 删除）
    private Integer deptDelete;

    // 部门员工数量
    @TableField(exist = false)
    private Integer empCount;

}
