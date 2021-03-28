package com.yzg.study.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.user.service.CloudMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户信息的管理  需要集成swagger-ui ，knife4j-ui
 */
@RefreshScope
@RestController
@RequestMapping("/rest/menus")
@Api(value = "菜单管理",tags = {"菜单接口"})
public class CloudMenuController {

    @Autowired
    private CloudMenuService cloudMenuService;
    /*
     * 菜单列表,非普通的查询所有，需要一个菜单树
     */
    @RequestMapping("/list")
    @ApiOperation(value = "菜单列表",produces = "查询所有菜单",httpMethod = "GET")
    public CloudResult getMenusList(){
       Map map = cloudMenuService.getMenusList();
       return  CloudResult.createByCodeSuccess(1,"菜单树查询成功！",map);

    }

    /*
     * 菜单添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "菜单添加",produces = "菜单添加功能",httpMethod = "POST")
    public  CloudResult save(@RequestBody CloudMenu cloudMenu){

        boolean save = cloudMenuService.save(cloudMenu);
         return  CloudResult.createByCodeSuccess(1,"成功！",save);
    }


    /*
     * 菜单更新
     */
    @PostMapping("/update")
    @ApiOperation(value = "菜单更新",produces = "菜单更新",httpMethod = "POST")
    public  CloudResult update(@RequestBody CloudMenu cloudMenu){

        boolean updateById = cloudMenuService.updateById(cloudMenu);
        return  CloudResult.createByCodeSuccess(1,"成功！",updateById);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "菜单的删除",produces = "菜单删除",httpMethod = "GET")
    public  CloudResult delete(@RequestBody List<String> idList){
        boolean removeById =false;
        //如果有子节点的必须先删除子节点才能删除
        for(String id:idList) {
            List<CloudMenu> cloudMenuList = cloudMenuService.list(new QueryWrapper<CloudMenu>().lambda().eq(CloudMenu::getParentId, id));
            if(!CollectionUtils.isEmpty(cloudMenuList)){
              return  CloudResult.createByErrorMessage("请先删除子节点才能删除该节点。");
            }
            removeById = cloudMenuService.removeById(id);
        }
        return  CloudResult.createByCodeSuccess(1,"成功！",removeById);
    }


}
