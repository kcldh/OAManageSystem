package com.cjj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Resource;
import com.cjj.mapper.ResourceMapper;
import com.cjj.service.IResourceService;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {


    /**
     *  根据角色ID得到资源
     * @param roleId    角色ID
     * @return          当前所有角色的资源
     */
    @Override
    public List<Resource> getResourcesByRoleId(int roleId){
        return baseMapper.getResourcesByRoleId(roleId);
    }

    /**
     *  得到所有的为逻辑删除的资源
     *
     * @return     所有未逻辑删除的资源
     */
    @Override
    public List<Resource> getResources() {
        return baseMapper.getResources();
    }

    /**
     *  查询所有的资源（包括逻辑删除的资源）
     * @param page  分页数据
     * @return      分页数据
     */
    @Override
    public Page<Resource> selectAll(Page<Resource> page){
        return baseMapper.selectAll(page);
    }

    @Override
    public Page<Resource> getResourceLikeName(Page<Resource> page, String likeName) {
        return baseMapper.getResourceLikeName(page, likeName);
    }

}
