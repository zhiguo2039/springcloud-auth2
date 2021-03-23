package com.yzg.study.auth2.controller;


import com.yzg.study.api.feign.client.ApiCloudUserClient;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 通过oauth2 进行接口api的跳转,这里面的可以直接与外界接触的，例如vue传过来的数据在这里面处理
 */
@RequestMapping("/rest/user")
@Api(value = "用户管理",tags = {"用户接口-oauth2"})
@RestController
public class ApiCloudUserController {

    @Autowired
    private ApiCloudUserClient apiCloudUserClient;

    @RequestMapping("/list")
    @ApiOperation(value = "用户列表",produces = "用户列表不分页",httpMethod = "GET")
    public CloudResult getList(){
        CloudResult cloudResult = apiCloudUserClient.getList();
        return  cloudResult;

    }

    @RequestMapping("/page")
    @ApiOperation(value = "用户列表分页",produces = "用户列表分页",httpMethod = "GET")
    public CloudResult page(@RequestBody JSONObject jsonObject ){

        if(jsonObject!=null&&!jsonObject.equals("")){
            int pageNum = jsonObject.getInteger("pageNum");
            int pageSize = jsonObject.getInteger("pageRow");
            String accountName = jsonObject.getString("accountName");
            CloudResult cloudResult = apiCloudUserClient.page(pageNum, pageSize, accountName);
            return  cloudResult;


        }
         return CloudResult.createByError();

    }

    @PostMapping("/save")
    @ApiOperation(value = "用户新增",produces = "用户新增",httpMethod = "POST")
    public CloudResult  save(@RequestBody CloudUserVo cloudUservo){
        return apiCloudUserClient.save(cloudUservo);

    }


    @PostMapping("/update")
    @ApiOperation(value = "用户更新",produces = "用户更新",httpMethod = "POST")
    public CloudResult  update(@RequestBody CloudUserVo cloudUservo){
        return apiCloudUserClient.update(cloudUservo);
    }

    /**
     * 用户的删除
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "用户删除",produces = "用户删除",httpMethod = "DELETE")
    public  CloudResult delete(@RequestBody CloudUserVo cloudUservo){
        return apiCloudUserClient.delete(cloudUservo);
    }


    /**
     * 获取用户的信息，一般在登录的时候获取
     * getInfo，获取需求：1.当前登录者的用户信息，2 当前登录者的权限角色信息，3，当前登录者的菜单信息
     */
    @RequestMapping("/getInfo")
    @ApiOperation(value = "获取当前用户信息Principal")
    public CloudResult user(Principal member) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        CloudResult cloudResult = apiCloudUserClient.getInfo(userName);
        return cloudResult;
    }

}
