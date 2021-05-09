package com.cjj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
public interface IRoleService extends IService<Role> {

    List<Role> getRoleByEmpId(int empId);

    Page<Role> selectAll(Page<Role> page);

    void saveRoleToRes(List<Integer> resId, int roleId);

    void deleteResForRole(Integer roleId);

    Page<Role> getRoles(Page<Role> page);

    /**
     *  根据员工的名称模糊查询
     * @param page      分页对象
     * @param likeName  模糊查询字符串
     * @return          分页数据
     */
    Page<Role> getRoleLikeName(Page<Role> page, String likeName);

}
