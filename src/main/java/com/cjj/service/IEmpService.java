package com.cjj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.Emp;
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
public interface IEmpService extends IService<Emp> {


    /**
     *  查询当前页所有非管理员的员工
     * @param page  分页对象
     * @return  分页对象
     */
    Page<Emp> selectAll(Page<Emp> page);


    /**
     *  根据部门ID分页查询
     * @param page  分页对象
     * @param deptId 部门ID
     * @return  分页对象
     */
    Page<Emp> selectPage(Page<Emp> page, @Param("id") int deptId);


    /**
     *  保存角色信息到员工信息中
     * @param roleId
     * @param empId
     */
    void saveRoleToEmp( List<Integer> roleId, int empId);

    /**
     *  从员工中删除角色信息
     * @param empId
     */
    void deleteRoleFromEmp(Integer empId);


    /**
     *  根据部门删除员工
     * @param deptId    部门ID
     * @param empDelete 是否删除
     * @return          是否成功
     */
    boolean deleteEmpFromDept( Integer deptId, Integer empDelete) ;

    /**
     *  根据部门ID得到部门员工信息
     *      不包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @return          分页数据
     */
    Page<Emp> selectPageByDept(Page<Emp> page, int deptId);

    /**
     *  得到当前部门的经理
     * @param deptId    部门ID
     * @return          当前部门经理信息
     */
    Emp getManager(int deptId);

    /**
     *  得到所有非管理员及经理的员工ID
     * @return  所有普通员工的ID
     */
    List<Integer> getNotManagerEmpId();


    /**
     *  得到所有非管理员的员工ID
     * @return  所有普通员工以及经理的ID
     */
    List<Integer> getNotManagerId();


    /**
     *  根据员工的名称模糊查询
     * @param page      分页对象
     * @param likeName  模糊查询字符串
     * @return          分页数据
     */
    Page<Emp> getEmpLikeName(Page<Emp> page, String likeName);

    /**
     *  根据部门ID得到部门员工信息
     *      不包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @param likeName  模糊查询关键字
     * @return          分页数据
     */
    Page<Emp> selectPageLikeName(Page<Emp> page, @Param("id") int deptId, @Param("name") String likeName);

}
