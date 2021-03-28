package com.yzg.study.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.entity.CloudUser;
import com.yzg.study.common.entity.CloudUserRole;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserInfoVo;
import com.yzg.study.common.vo.CloudUserVo;
import com.yzg.study.user.service.CloudRoleService;
import com.yzg.study.user.service.CloudUserRoleService;
import com.yzg.study.user.service.CloudUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息的管理  需要集成swagger-ui ，knife4j-ui
 */
@RefreshScope
@RestController
@RequestMapping("/rest/user")
@Api(value = "用户管理",tags = {"用户接口"})
public class CloudUserController {

    @Autowired
    private CloudUserService cloudUserService;
    @Autowired
    private CloudUserRoleService cloudUserRoleService;
    @Autowired
    private CloudRoleService cloudRoleService;


    /*
     * 用户列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "用户列表",produces = "用户列表不分页",httpMethod = "GET")
    public CloudResult getList(){
        CloudResult cloudResult = new CloudResult();
        List<CloudUser> userList = cloudUserService.list();
        cloudResult.setStatus(1);
        cloudResult.setMsg("用户列表不分页查询成功");
        cloudResult.setData(userList);
        return  cloudResult;


    }


    /*
     * 用户列表分页 ,pageNum ，pageSize, accountName
     */
    @RequestMapping("/page")
    @ApiOperation(value = "用户列表分页",produces = "用户列表分页",httpMethod = "GET")
    public CloudResult page(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                            @RequestParam(value = "accountName",required = false) String accountName){
        CloudResult cloudResult = new CloudResult();
        IPage<CloudUser> iPage = new Page<>(pageNum,pageSize);
        IPage<CloudUser> cloudUserIPage;
                if(accountName==null) {
                    cloudUserIPage = cloudUserService.page(iPage);
                } else {
                    cloudUserIPage =cloudUserService.page(iPage,new QueryWrapper<CloudUser>().lambda().like(CloudUser::getAccountName,accountName));
                }


        List<CloudUser> records = cloudUserIPage.getRecords();
         List list = new ArrayList();
        // 用户已经获取成功，接下来，获取该用户所含有的权限
        // user user-role ,role
        for(CloudUser cloudUser:records){
           // 通过user表信息进行查询user-role,两种方案：1个用户可以多种角色，也可以设计一种角色
            CloudUserRole cloudUserRole = cloudUserRoleService.getOne(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getUserId, cloudUser.getId()));
           // 根据roleId查询对应的角色
            if(cloudUserRole!=null){
                CloudRole cloudRole = cloudRoleService.getById(cloudUserRole.getRoleId());
                CloudUserVo cloudUserVo = new CloudUserVo();
                BeanUtils.copyProperties(cloudUser,cloudUserVo);
                cloudUserVo.setCloudRole(cloudRole);
                list.add(cloudUserVo);
            }
        }
        cloudUserIPage.setRecords(list);
        cloudResult.setStatus(1);
        cloudResult.setMsg("分页含有权限查询成功");
        cloudResult.setData(cloudUserIPage);
        return  cloudResult;


    }

    /**
     * 用户添加： 1 插入用户表，2插入用户对应的权限表  vue 传递requestbody
     */
    @PostMapping("/save")
    @ApiOperation(value = "用户新增",produces = "用户新增",httpMethod = "POST")
    public CloudResult  save(@RequestBody CloudUserVo cloudUservo){
        CloudResult cloudResult = new CloudResult();
        if(cloudUservo!=null&&!cloudUservo.equals("")){
            int i = cloudUserService.saveUserAndRole(cloudUservo);
            if(i>0) {
                cloudResult.setStatus(1);
                cloudResult.setMsg("用户新增成功");
                cloudResult.setData(i);
                return  cloudResult;
            } else {
                cloudResult.setStatus(-1);
                cloudResult.setMsg("用户新增失败！");
                cloudResult.setData(null);
                return cloudResult;
            }

        } else {
            cloudResult.setStatus(-1);
            cloudResult.setMsg("用户新增失败！");
            cloudResult.setData(null);
            return cloudResult;
        }

    }

    /**
     * 用户的更新
     */
    @PostMapping("/update")
    @ApiOperation(value = "用户更新",produces = "用户更新",httpMethod = "POST")
    public CloudResult  update(@RequestBody CloudUserVo cloudUservo){
        CloudResult cloudResult = new CloudResult();
        if(cloudUservo!=null&&!cloudUservo.equals("")){
            int i = cloudUserService.updateUserAndRole(cloudUservo);
            if(i>0) {
                cloudResult.setStatus(1);
                cloudResult.setMsg("用户更新成功");
                cloudResult.setData(i);
                return  cloudResult;
            } else {
                cloudResult.setStatus(-1);
                cloudResult.setMsg("用户更新失败！");
                cloudResult.setData(null);
                return cloudResult;
            }

        } else {
            cloudResult.setStatus(-1);
            cloudResult.setMsg("用户更新失败！");
            cloudResult.setData(null);
            return cloudResult;
        }
    }

    /**
     * 用户的删除
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "用户删除",produces = "用户删除",httpMethod = "DELETE")
    public  CloudResult delete(@RequestBody CloudUserVo cloudUservo){
        CloudResult cloudResult = new CloudResult();
        // 1.删除用户角色关联表
        if(cloudUservo!=null&&!cloudUservo.equals("")){
            CloudUserRole cloudUserRole = cloudUserRoleService.getOne(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getRoleId, cloudUservo.getRoleId()));
            boolean removeById = cloudUserRoleService.removeById(cloudUserRole.getId());
            if(removeById){
                // 2.删除用户表
                boolean byId = cloudUserService.removeById(cloudUservo.getId());
                if(byId){
                    cloudResult.setStatus(1);
                    cloudResult.setMsg("用户删除成功");
                    cloudResult.setData(byId);


                }
            }

        } else {
            cloudResult.setStatus(-1);
            cloudResult.setMsg("用户删除失败！");
            cloudResult.setData(null);

        }
        return cloudResult;
    }

    /**
     * 获取用户的信息，一般在登录的时候获取
     * getInfo，获取需求：1.当前登录者的用户信息，2 当前登录者的权限角色信息，3，当前登录者的菜单信息
     */
    @RequestMapping("/getInfo")
    @ApiOperation(value = "获取当前登录者的用户信息",produces = "用户信息和权限，菜单",httpMethod = "GET")
    public  CloudResult getInfo(@RequestParam("userName") String userName) {
        CloudUserInfoVo cloudUserInfoVo = cloudUserService.getInfo(userName);

        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("用户信息查询成功");
        cloudResult.setData(cloudUserInfoVo);
        return  cloudResult;

    }

    /**
     * 获取用户账户查询信息
     */
    @RequestMapping("/api")
    @ApiOperation(value = "获取当前登录者的用户信息",produces = "用户信息和权限，菜单",httpMethod = "GET")
    public  CloudResult getUserByUserName(@RequestParam("accountName") String accountName) {
        CloudUser cloudUser =  cloudUserService.getUserByUserName(accountName);
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("用户查询成功");
        cloudResult.setData(cloudUser);
        return  cloudResult;

    }
}
