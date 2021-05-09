package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Resource;
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
public interface ResourceMapper extends BaseMapper<Resource> {


    /**
     *  得到所有的为逻辑删除的资源
     *
     * @return     所有未逻辑删除的资源
     */
    List<Resource> getResources();

    /**
     *  根据角色ID得到资源
     * @param roleId    角色ID
     * @return          当前所有角色的资源
     */
    List<Resource> getResourcesByRoleId(int roleId);

    /**
     *  查询所有的资源（包括逻辑删除的资源）
     * @param page  分页数据
     * @return      分页数据
     */
    Page<Resource> selectAll(Page<Resource> page);

    /**
     *  根据资源ID得到当前资源的子资源
     * @param resId     资源DI
     * @return          当前资源子资源
     */
    List<Resource> getResourceByParentId(int resId);


    /**
     *  根据员工的名称模糊查询
     * @param page      分页对象
     * @param likeName  模糊查询字符串
     * @return          分页数据
     */
    Page<Resource> getResourceLikeName(Page<Resource> page, @Param("name") String likeName);

}
