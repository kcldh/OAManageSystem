package com.cjj.a;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.bean.Notice;
import com.cjj.bean.Resource;
import com.cjj.bean.Role;
import com.cjj.bean.Task;
import com.cjj.config.MyDBConfig;
import com.cjj.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class AApplicationTests {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IEmpService empService;

    @Autowired
    private MyDBConfig myDBConfig;
    @Autowired
    private MyDBConfig myDBConfig1;


    @Autowired
    private INoticeService noticeService;

    @Autowired
    private ITaskService taskService;

    private final Page<Role> page = new Page<>();

    private final Page<Task> page1 = new Page();

    private final Page<Notice> page2 = new Page<>();



    @Test
    void test3() {

        roleService.getRoleLikeName(page, "经").getRecords()
        .forEach(role -> System.out.println(role.getRoleName()));

    }


    @Test
    void test2() {
        System.out.println(
                taskService.getSuccessTaskByEmpId(
                        page1, 4).getRecords());
    }

    @Test
    void test1() {

        Integer integer = 127;
        Integer integer3 = 127;

        System.out.println(integer == integer3);

        Integer integer1 = -128;
        Integer integer4 = -128;

        System.out.println(integer1 == integer4);

        Integer integer2 = -129;
        Integer integer5 = -129;

        System.out.println(integer2 == integer5);

//        System.out.println(resourceService.getResources(new Page<>()).getRecords());

//        List<Integer> resId = new ArrayList<>();

//        resId.add(1);
//        resId.add(2);

//        roleService.saveRoleToRes(resId,3);

    }



    @Test
    void test() {
        System.out.println(myDBConfig == myDBConfig1);
    }

    @Test
    void contextLoads() {

//        System.out.println(empService.selectPage(new Page<>(1, 10), 1).getRecords());

        List<Role> roles = roleService.getRoleByEmpId(1);

        List<Resource> resources = new ArrayList<>();
        for (Role role : roles) {
            List<Resource> list = null;

                list = resourceService.list(new QueryWrapper<Resource>()
                        .eq("parent_id", "0")
                        .or().isNull("parent_id"));

            resources.addAll(resourceService.getResourcesByRoleId(role.getRoleId()));
//            for (Resource resource : list) {
//                if(!resources.contains(resource)) {
//                    resource.setResourcePage(resourceService.list(
//                            new QueryWrapper<Resource>().eq("res_id", resource.getResId())));
//                    resources.add(resource);
//                }
//            }
        }
        System.out.println(resources);
    }

}
