package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     *  根据员工ID得到当前员工的所有角色
     *      只查询未逻辑删除的角色
     * @param empId 员工ID
     * @return      当前员工的所有角色
     */
    List<Role> getRoleByEmpId(int empId);

    /**
     *  查询所有的角色
     *  （包括逻辑删除的角色）
     * @param page  分页数据
     * @return      分页数据
     */
    Page<Role> selectAll(Page<Role> page);

    /**
     *      保存资源到角色中
     * @param resId 资源ID
     * @param roleId    角色ID
     */
    void saveRoleToRes(@Param("resId") List<Integer> resId, @Param("roleId") int roleId);

    /**
     *  根据角色ID删除角色的资源
     * @param roleId    角色ID
     */
    void deleteResForRole(Integer roleId);

    /**
     *  得到所有的角色
     * @param page  分页数据
     * @return  分页数据
     */
    Page<Role> getRoles(Page<Role> page);


    /**
     *  根据员工的名称模糊查询
     * @param page      分页对象
     * @param likeName  模糊查询字符串
     * @return          分页数据
     */
    Page<Role> getRoleLikeName(Page<Role> page, @Param("name") String likeName);

}
