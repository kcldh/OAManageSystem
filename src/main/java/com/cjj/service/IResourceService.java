package com.cjj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.bean.Resource;
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
public interface IResourceService extends IService<Resource> {

    List<Resource> getResourcesByRoleId(int roleId);

    List<Resource> getResources();

    Page<Resource> selectAll(Page<Resource> page);

    /**
     *  根据员工的名称模糊查询
     * @param page      分页对象
     * @param likeName  模糊查询字符串
     * @return          分页数据
     */
    Page<Resource> getResourceLikeName(Page<Resource> page, String likeName);

}
