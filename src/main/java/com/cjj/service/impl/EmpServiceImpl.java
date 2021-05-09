package com.cjj.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Emp;
import com.cjj.mapper.EmpMapper;
import com.cjj.service.IEmpService;
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
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {


    /**
     *  查询所有的资源， 包括逻辑删除的资源
     * @param page      分页数据
     * @return          分页数据
     */
    @Override
    public Page<Emp> selectAll(Page<Emp> page) {

        return baseMapper.selectAll(page);
    }

    /**
     *  根据部门ID得到部门员工信息
     *      不包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @return          分页数据
     */
    @Override
    public Page<Emp> selectPage(Page<Emp> page, int deptId) {
        return baseMapper.selectPage(page, deptId);
    }


    /**
     *  新增角色信息到员工中
     * @param roleId    角色·ID
     * @param empId     员工ID
     */
    @Override
    public void saveRoleToEmp(List<Integer> roleId, int empId) {
        baseMapper.saveRoleToEmp(roleId, empId);
    }


    /**
     *  删除当前员工的角色
     * @param empId 员工ID
     */
    @Override
    public void deleteRoleFromEmp(Integer empId) {
        baseMapper.deleteRoleFromEmp(empId);
    }

    /**
     *  根据部门删除员工
     * @param deptId    部门ID
     * @param empDelete 是否删除
     * @return          是否成功
     */
    @Override
    public boolean deleteEmpFromDept(Integer deptId, Integer empDelete) {
        return baseMapper.deleteEmpFromDept(deptId, empDelete);
    }




    /**
     *  根据部门查询员工信息
     *      包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @return          所有的信息
     */
    @Override
    public Page<Emp> selectPageByDept(Page<Emp> page, int deptId) {
        return baseMapper.selectPageByDept(page, deptId);
    }

    @Override
    public Emp getManager(int deptId) {

        List<Emp> manager = baseMapper.getManager(deptId);
        if(manager.size() > 0) {
            return manager.get(0);
        }

        return null;
    }

    @Override
    public List<Integer> getNotManagerEmpId() {
        return baseMapper.getNotManagerEmpId();
    }

    @Override
    public List<Integer> getNotManagerId() {
        return baseMapper.getNotManagerId();
    }

    @Override
    public Page<Emp> getEmpLikeName(Page<Emp> page, String likeName) {
        return baseMapper.getEmpLikeName(page, likeName);
    }

    @Override
    public Page<Emp> selectPageLikeName(Page<Emp> page, int deptId, String likeName) {
        return baseMapper.selectPageLikeName(page, deptId, likeName);
    }
}
