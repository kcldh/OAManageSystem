package com.cjj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Emp;
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
public interface EmpMapper extends BaseMapper<Emp> {

    /**
     *  查询所有的资源， 包括逻辑删除的资源
     * @param page      分页数据
     * @return          分页数据
     */
    Page<Emp> selectAll(Page<Emp> page);


    /**
     *  根据员工ID查询员工
     * @param empId 员工ID
     * @return  员工
     */
    Emp selectEmpById(Integer empId);

    /**
     *  根据部门ID得到部门员工信息
     *      不包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @return          分页数据
     */
    Page<Emp> selectPage(Page<Emp> page, @Param("id") int deptId);

    /**
     *  根据部门查询员工信息
     *      包括逻辑删除的员工信息
     * @param page      分页数据
     * @param deptId    部门ID
     * @return          所有的信息
     */
    Page<Emp> selectPageByDept(Page<Emp> page, @Param("id") int deptId);

    /**
     *  新增角色信息到员工中
     * @param roleId    角色·ID
     * @param empId     员工ID
     */
    void saveRoleToEmp(@Param("roleId") List<Integer> roleId, @Param("empId") int empId);

    /**
     *  删除当前员工的角色
     * @param empId 员工ID
     */
    void deleteRoleFromEmp(Integer empId);

    /**
     *  根据部门删除员工
     * @param deptId    部门ID
     * @param empDelete 是否删除
     * @return          是否成功
     */
    boolean deleteEmpFromDept(@Param("deptId") Integer deptId, @Param("delete") Integer empDelete);

    /**
     *  查询当前部门的所有员工
     * @param deptId    部门ID
     * @return          所有员工的数量
     */
    Integer selectEmpCountByDept(Integer deptId);

    /**
     *  得到当前部门的经理
     * @param deptId    部门ID
     * @return          当前部门经理信息
     */
    List<Emp> getManager(int deptId);


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
    Page<Emp> getEmpLikeName(Page<Emp> page, @Param("name") String likeName);


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
