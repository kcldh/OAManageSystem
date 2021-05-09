package com.cjj.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.bean.Dept;
import com.cjj.mapper.DeptMapper;
import com.cjj.mapper.EmpMapper;
import com.cjj.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈佳俊
 * @since 2021-04-14
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    /**
     * 查询所有的部门
     *
     * @param page 分页
     * @return 分页数据
     */
    public Page<Dept> selectAll(Page<Dept> page) {
        return baseMapper.selectAll(page);
    }

}
