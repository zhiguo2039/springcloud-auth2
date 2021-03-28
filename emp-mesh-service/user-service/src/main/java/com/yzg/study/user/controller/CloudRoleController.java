package com.yzg.study.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.vo.CloudMenuVo;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.user.service.CloudRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户角色的管理  需要集成swagger-ui ，knife4j-ui
 */
@RefreshScope
@RestController
@RequestMapping("/rest/role")
@Api(value = "用户角色",tags = {"用户角色接口"})
public class CloudRoleController {

    @Autowired
    private CloudRoleService cloudRoleService;

    /**
     * 查询所有角色，但是含有user
     * @return
     */
    @RequestMapping("/list")
    @ApiOperation(value = "角色列表含用户",produces = "角色信息含用户",httpMethod = "GET")
    public CloudResult list(){
          //1.查询所有角色，2 查询该角色下面的所有用户
        Map map = cloudRoleService.getRoleAndUserList();

        return  CloudResult.createByCodeSuccess(1,"成功",map);
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping("/commonList")
    @ApiOperation(value = "角色列表不含用户",produces = "角色信息不含用户",httpMethod = "GET")
    public CloudResult getList(){

        List<CloudRole> list = cloudRoleService.list();
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("成功！");
        cloudResult.setData(list);
        return  cloudResult;
    }


    /**
     * save角色
     * @return
     */
    @RequestMapping("/save")
    @ApiOperation(value = "角色列表保存",produces = "角色信息保存",httpMethod = "POST")
    public CloudResult save(@RequestBody JSONObject jsonObject){

        if(jsonObject!=null&&!jsonObject.equals("")) {
            String rolecn = jsonObject.getString("roleCn");
            String describeText = jsonObject.getString("describeText");
            String isEnable = jsonObject.getString("isEnable");
            CloudRole cloudRole = new CloudRole();
            cloudRole.setDescribeText(describeText);
            cloudRole.setRoleCn(rolecn);
            //其他的同学们自己扩展
            if(isEnable!=null&&!isEnable.equals("")) {
                if(isEnable.equals("true"))
                cloudRole.setIsEnable("1");
            } else {
                cloudRole.setIsEnable("0");
            }
            boolean saveOrUpdate = cloudRoleService.saveOrUpdate(cloudRole);
            return  CloudResult.createByCodeSuccess(1,"成功",saveOrUpdate);
        }
        return  CloudResult.createByError();

    }
    /**
     * update角色
     * @return
     */
    @RequestMapping("/update")
    @ApiOperation(value = "角色列表更新",produces = "角色信息更新",httpMethod = "POST")
    public CloudResult update(@RequestBody JSONObject jsonObject){

        if(jsonObject!=null&&!jsonObject.equals("")) {
            String id = jsonObject.getString("id");
            String rolecn = jsonObject.getString("roleCn");
            String describeText = jsonObject.getString("describeText");
            String isEnable = jsonObject.getString("isEnable");
            CloudRole cloudRole = new CloudRole();
            cloudRole.setDescribeText(describeText);
            cloudRole.setRoleCn(rolecn);
            cloudRole.setId(id);
            //其他的同学们自己扩展
            if(isEnable!=null&&!isEnable.equals("")) {
                if(isEnable.equals("true"))
                    cloudRole.setIsEnable("1");
            } else {
                cloudRole.setIsEnable("0");
            }
            boolean saveOrUpdate = cloudRoleService.saveOrUpdate(cloudRole);
            return  CloudResult.createByCodeSuccess(1,"成功",saveOrUpdate);
        }
        return  CloudResult.createByError();

    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "角色列表删除",produces = "角色信息删除",httpMethod = "POST")
    public  CloudResult delete(@RequestParam("roleId") String roleId){
        boolean removeById = cloudRoleService.removeById(roleId);
        if(removeById){
            return CloudResult.createByCodeSuccess(1,"成功删除！",removeById);
        }
        return CloudResult.createByError();

    }

    /**
     * 根据角色获取所有Element树形结构的菜单
     */
    @RequestMapping("/getAllMenus")
    @ApiOperation(value = "角色菜单",produces = "角色菜单",httpMethod = "POST")
    public  CloudResult getAllMenus(){

        List<CloudMenuVo> cloudMenuVoList = cloudRoleService.getAllMenusByELTree();
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("成功！");
        cloudResult.setData(cloudMenuVoList);
        return  cloudResult;
    }

    /**
     * 根据角色设置菜单
     */
    @RequestMapping("/setMenus")
    @ApiOperation(value = "角色菜单设置",produces = "角色菜单设置",httpMethod = "POST")
    public  CloudResult setMenus(@RequestBody JSONObject jsonObject){
       int i = cloudRoleService.setMenus(jsonObject);
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("成功！");
        cloudResult.setData(i);
        return  cloudResult;
    }

}
