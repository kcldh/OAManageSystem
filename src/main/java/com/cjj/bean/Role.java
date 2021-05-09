package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *      角色表实体类
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
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;


    // 角色ID
    @TableId(value = "role_Id", type = IdType.AUTO)
    private Integer roleId;

    // 角色名称
    private String roleName;

    // 角色是否删除
    private Integer roleDelete;

    // 当前角色的所有资源
    @TableField(exist = false)
    private List<Resource> resources;

    // 存储当前角色所有的资源ID
    @TableField(exist = false)
    private List<Integer> res;


    /**
     * 得到当前资源的名称
     * @return  资源名称
     */
    public String getResource() {

        String str = "";

        // 判断当前是否有资源
        if(resources != null) {

            // 遍历所有的资源
            for (Resource resource : resources) {

                // 拼接资源名称
                str += resource.getResName()+",";
            }
        }


        return str.contains(",") ?str.substring(0, str.lastIndexOf(",")):str;
    }

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
