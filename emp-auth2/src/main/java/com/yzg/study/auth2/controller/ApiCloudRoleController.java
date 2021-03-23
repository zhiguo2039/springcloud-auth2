package com.yzg.study.auth2.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yzg.study.api.feign.client.ApiCloudRoleClient;
import com.yzg.study.common.vo.CloudResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统-角色表 (CloudRole)表控制层
 *
 * @author Array老师
 */
@RestController
@RequestMapping("/rest/role")
@Api(produces = "系统-角色表 接口")

public class ApiCloudRoleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ApiCloudRoleClient apiCloudRoleClient;

    /**
     * 不分页查询所有数据： 获取角色里面有多少个用户是该角色   admin  3人（张三，李四，王五）
     *
     * @return 所有数据
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "角色表 列表不分页", httpMethod = "POST", response = CloudResult.class)
    public CloudResult list() {
        CloudResult cloudResult = apiCloudRoleClient.list();
        return  cloudResult;
    }

    /**
     * 查询不分页数据
     */
    @RequestMapping(value = "/commonList")
    @ApiOperation(value = "角色表 列表 角色本身不含其它", httpMethod = "POST", response = CloudResult.class)
    public CloudResult getList(){
        CloudResult cloudResult = apiCloudRoleClient.getList();
        return  cloudResult;
    }


    @PostMapping(value = "/getAllMenus")
    @ApiOperation(value = "角色表 列表 菜单", httpMethod = "POST", response = CloudResult.class)
    public CloudResult getAllMenus(){
        CloudResult cloudResult = apiCloudRoleClient.getAllMenus();
        return  cloudResult;
    }

    /**
     * 分配菜单权限
     */
    @PostMapping("/setMenus")
    @ApiOperation(value = "获取系统管理-角色表 列表", httpMethod = "POST", response = CloudResult.class)
    public CloudResult setMenus(@RequestBody JSONObject jsonObject){
        CloudResult cloudResult =  apiCloudRoleClient.setMenus(jsonObject);

        return  cloudResult;
    }


    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存角色", httpMethod = "POST", response = CloudResult.class)
    public CloudResult save(@RequestBody JSONObject jsonObject){
        CloudResult cloudResult =  apiCloudRoleClient.save(jsonObject);
        return  cloudResult;
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "更新角色", httpMethod = "POST", response = CloudResult.class)
    public CloudResult update(@RequestBody JSONObject jsonObject) {
        CloudResult cloudResult =  apiCloudRoleClient.update(jsonObject);
        return  cloudResult;
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除角色 ", httpMethod = "POST", response = CloudResult.class)
    public CloudResult delete(@RequestParam("roleId") String roleId) {
        CloudResult cloudResult =  apiCloudRoleClient.delete(roleId);
        return  cloudResult;
    }
}