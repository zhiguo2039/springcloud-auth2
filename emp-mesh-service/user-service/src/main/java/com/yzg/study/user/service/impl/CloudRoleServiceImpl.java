package com.yzg.study.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.entity.CloudRoleMenu;
import com.yzg.study.common.entity.CloudUser;
import com.yzg.study.common.entity.CloudUserRole;
import com.yzg.study.common.vo.CloudMenuVo;
import com.yzg.study.common.vo.CloudRoleVo;
import com.yzg.study.user.mapper.*;
import com.yzg.study.user.service.CloudRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudRoleServiceImpl extends ServiceImpl<CloudRoleMapper, CloudRole> implements CloudRoleService {

    @Autowired
    private  CloudRoleMapper cloudRoleMapper;
    @Autowired
    private CloudUserRoleMapper cloudUserRoleMapper;

    @Autowired
    private CloudUserMapper cloudUserMapper;

    @Autowired
    private CloudMenuMapper cloudMenuMapper;

    @Autowired
    private CloudRoleMenuMapper cloudRoleMenuMapper;

    @Override
    public Map getRoleAndUserList() {
       //除了获取用户和角色之外，还要为复选框判断
        List<CloudRoleVo> cloudRoleVoList = cloudRoleMapper.getList();
        for(CloudRoleVo cloudRoleVo:cloudRoleVoList){
            //查找该角色下面的所有用户， userRole
            String roleId=cloudRoleVo.getId();
            List<CloudUser> cloudUserList = new ArrayList<>();
            List<CloudUserRole> cloudUserRoleList = cloudUserRoleMapper.selectList(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getRoleId, roleId));
            for(CloudUserRole cloudUserRole:cloudUserRoleList) {
                CloudUser cloudUser = cloudUserMapper.selectById(cloudUserRole.getUserId());
                cloudUserList.add(cloudUser);
            }
              cloudRoleVo.setCloudUserList(cloudUserList);
            List<CloudMenuVo> cloudMenuVoList = new ArrayList<>();
            // 复选框
            List<CloudMenuVo> menuListByPId = cloudMenuMapper.getMenuListByPId("0");
            List<String>  checkedIds = new ArrayList();
            for(CloudMenuVo cloudMenuVo:menuListByPId){
                //获取菜单为复选框准备
                List<CloudMenuVo> menuList = cloudMenuMapper.getMenuList(cloudMenuVo.getId(),cloudRoleVo.getId());
                if(menuList!=null&&!menuList.equals("")){
                    for (CloudMenuVo cloudMenuVo1:menuList) {
                        checkedIds.add(cloudMenuVo1.getId());
                    }
                }
                cloudMenuVo.setMenuList(menuList);
                cloudMenuVoList.add(cloudMenuVo);
            }
            cloudRoleVo.setCheckedIds(checkedIds);
            cloudRoleVo.setYunMenusVo(cloudMenuVoList);

        }
         Map map = new HashMap();
        map.put("list",cloudRoleVoList);


        return map;
    }

    @Override
    public List<CloudMenuVo> getAllMenusByELTree() {
        List<CloudMenuVo> menuListByPId = cloudMenuMapper.getMenuListByPId("0");
        List<CloudMenuVo> newMenuList = new ArrayList<>();
        for (CloudMenuVo cloudMenuVo:menuListByPId){
            List<CloudMenuVo> permissionList = cloudMenuMapper.getMenuListByPId(cloudMenuVo.getId());
            cloudMenuVo.setMenuList(permissionList);
            newMenuList.add(cloudMenuVo);
        }
        return newMenuList  ;
    }

    @Override
    public int setMenus(JSONObject jsonObject) {
        int count =0;
        //[100,101,102]
        String ids = jsonObject.getString("ids").replace("\"", "");
        String roleId = jsonObject.getString("roleId");

        //选中的角色id
        String[] idsArray = ids.substring(1, ids.length() - 1).split(",");

        //先删除，在插入
        int delete = cloudRoleMenuMapper.delete(new QueryWrapper<CloudRoleMenu>().lambda().eq(CloudRoleMenu::getRoleId, roleId));
        if(delete>0){
           for(String id:idsArray) {
               CloudRoleMenu cloudRoleMenu = new CloudRoleMenu();
               cloudRoleMenu.setRoleId(roleId);
               cloudRoleMenu.setMenuId(id);
               int i = cloudRoleMenuMapper.insert(cloudRoleMenu);
               count=count+i;
           }
        }
        return count;
    }
}
