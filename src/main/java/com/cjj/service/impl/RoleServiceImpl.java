package com.cjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Role;
import com.cjj.mapper.RoleMapper;
import com.cjj.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    /**
     *  根据员工ID得到当前员工的所有角色
     *      只查询未逻辑删除的角色
     * @param empId 员工ID
     * @return      当前员工的所有角色
     */
    @Override
    public List<Role> getRoleByEmpId(int empId) {
        return baseMapper.getRoleByEmpId(empId);
    }

    /**
     *  查询所有的角色
     *  （包括逻辑删除的角色）
     * @param page  分页数据
     * @return      分页数据
     */
    @Override
    public Page<Role> selectAll(Page<Role> page) {

        return baseMapper.selectAll(page);
    }

    /**
     *      保存资源到角色中
     * @param resId 资源ID
     * @param roleId    角色ID
     */
    @Override
    public void saveRoleToRes(List<Integer> resId, int roleId) {
        baseMapper.saveRoleToRes(resId, roleId);
    }

    /**
     *  根据角色ID删除角色的资源
     * @param roleId    角色ID
     */
    @Override
    public void deleteResForRole(Integer roleId) {
        baseMapper.deleteResForRole(roleId);
    }

    /**
     *  得到所有的角色
     * @param page  分页数据
     * @return  分页数据
     */
    @Override
    public Page<Role> getRoles(Page<Role> page) {
        return baseMapper.getRoles(page);
    }


    @Override
    public Page<Role> getRoleLikeName(Page<Role> page, String likeName) {
        return baseMapper.getRoleLikeName(page, likeName);
    }

}
