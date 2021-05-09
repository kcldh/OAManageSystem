package com.cjj.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *      资源表实体类
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
public class Resource extends Model<Resource> {

    private static final long serialVersionUID = 1L;

    // 资源ID
    @TableId(value = "res_Id", type = IdType.AUTO)
    private Integer resId;

    // 资源名称
    private String resName;

    // 资源地址
    private String resUrl;

    // 资源图标
    private String resIco;

    // 父资源ID
    private String parentId;

    // 资源是否删除
    private Integer resDelete;

    // 资源详情
    private String resDetails;

    // 当前资源的子资源
    @TableField(exist = false)
    private List<Resource> resourcePage;

    /**
     *  当前是否为一级目录
     *      如果为一级目录
     *          返回一级目录
     *      否则
     *          返回目录地址
     * @return
     */
    public String getResUrl1() {
        return resUrl == null ? "一级目录" : resUrl;
    }


    /**
     *  得到子资源的名字
     * @return  子资源名称
     */
    public String getFindName() {
        String str = "暂无";

        // 如果子资源不为null
        if(resourcePage != null) {

            // 如果有子资源
            if(resourcePage.size() > 0) {
                str = "";

                // 遍历所有的子资源
                for (Resource resource : resourcePage) {
                    str += resource.getResName() + ",";
                }
            }
        }

        return str.contains(",") ?str.substring(0, str.lastIndexOf(",")):str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return resource.getResId() == this.getResId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, resName, resUrl, parentId, resDelete, resDetails);
    }

    @Override
    protected Serializable pkVal() {
        return this.resId;
    }

}
