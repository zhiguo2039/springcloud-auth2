package com.yzg.study.auth2.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yzg.study.api.feign.client.ApiCloudMenuClient;
import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统-菜单表 (CloudMenu)表控制层
 *
 * @author Array老师
 * @since 2020-03-21 15:19:29
 */
@RestController
@RequestMapping("/rest/menus")
@Api(produces = "系统-菜单表表接口")
public class ApiCloudMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private ApiCloudMenuClient apiCloudMenuClient;


    @PostMapping(value = "/list")
    @ApiOperation(value = "获取菜单树", httpMethod = "POST", response = CloudResult.class)
    public CloudResult list(){
        CloudResult cloudResult  = apiCloudMenuClient.list();
        return  cloudResult;
    }

    /**
     * 新增数据
     *
     * @param cloudMenu 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存菜单 ", httpMethod = "POST", response = CloudResult.class)
    public CloudResult save(@RequestBody CloudMenu cloudMenu) {
        CloudResult cloudResult  = apiCloudMenuClient.save(cloudMenu);
        return  cloudResult;
    }

    /**
     * 修改数据
     *
     * @param cloudMenu 实体对象
     * @return 修改结果
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "保存菜单 ", httpMethod = "POST", response = CloudResult.class)
    public CloudResult update(@RequestBody CloudMenu cloudMenu) {
        CloudResult cloudResult  = apiCloudMenuClient.update(cloudMenu);
        return  cloudResult;
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", response = CloudResult.class)
    public CloudResult delete(@RequestParam("idList") List<String> idList) {
        CloudResult cloudResult  = apiCloudMenuClient.delete(idList);
        return  cloudResult;
    }

}