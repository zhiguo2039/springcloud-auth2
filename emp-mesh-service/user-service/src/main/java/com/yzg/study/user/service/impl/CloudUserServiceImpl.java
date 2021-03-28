package com.yzg.study.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.entity.CloudUser;
import com.yzg.study.common.entity.CloudUserRole;
import com.yzg.study.common.vo.CloudUserInfoVo;
import com.yzg.study.common.vo.CloudUserVo;
import com.yzg.study.user.mapper.CloudMenuMapper;
import com.yzg.study.user.mapper.CloudRoleMapper;
import com.yzg.study.user.mapper.CloudUserMapper;
import com.yzg.study.user.mapper.CloudUserRoleMapper;
import com.yzg.study.user.service.CloudUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * mybatisplus的写法，可以参考老师主页的mybatisplus的专题
 */
@Service
@Transactional
public class CloudUserServiceImpl  extends ServiceImpl<CloudUserMapper, CloudUser> implements CloudUserService {

    @Autowired
    private  CloudUserMapper cloudUserMapper;

    @Autowired
    private CloudUserRoleMapper cloudUserRoleMapper;
    @Autowired
    private CloudRoleMapper cloudRoleMapper;

    @Autowired
    private CloudMenuMapper cloudMenuMapper;
    @Override
    public int saveUserAndRole(CloudUserVo cloudUservo) {
        //1 插入cloudUser表
        CloudUser cloudUser = new CloudUser();
        BeanUtils.copyProperties(cloudUservo,cloudUser);
        // 加密 pwd 123456  不能明文存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode(cloudUser.getPassWord());
        cloudUser.setPassWord(pass);
        //如果数据库表没默认设置
        cloudUser.setIsLock("0");
        int i = cloudUserMapper.insert(cloudUser);
         if(i>0) {
        //2 插入cloudUserRole表
             CloudUserRole cloudUserRole = new CloudUserRole();
             cloudUserRole.setUserId(cloudUser.getId());
             cloudUserRole.setRoleId(cloudUservo.getRoleId());
             int n = cloudUserRoleMapper.insert(cloudUserRole);
             return n;
         }


        return 0;
    }
    @Override
    public int updateUserAndRole(CloudUserVo cloudUservo) {
        //1 更新cloudUser表
        CloudUser cloudUser = new CloudUser();
        BeanUtils.copyProperties(cloudUservo,cloudUser);
        // 加密 pwd 123456  不能明文存储
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pass = passwordEncoder.encode(cloudUser.getPassWord());
       //cloudUser.setPassWord(pass);
        //如果数据库表没默认设置
        //  cloudUser.setIsLock("0");
        int i = cloudUserMapper.updateById(cloudUser);
        if(i>0) {
            //2 插入cloudUserRole表
            CloudUserRole userRole = cloudUserRoleMapper.selectOne(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getUserId, cloudUser.getId()));
            userRole.setRoleId(cloudUservo.getRoleId());
            int n = cloudUserRoleMapper.updateById(userRole);
            return n;
        }


        return 0;
    }

    @Override
    public CloudUserInfoVo getInfo(String userName) {
        // 1.查询用户信息
        CloudUser cloudUser = cloudUserMapper.selectOne(new QueryWrapper<CloudUser>().lambda().eq(CloudUser::getAccountName, userName));
        CloudUserInfoVo cloudUserInfoVo = new CloudUserInfoVo();
        BeanUtils.copyProperties(cloudUser,cloudUserInfoVo);

        // 2.查询用户角色
        Set<String> roles = new HashSet<String>();
        // 查询useRole关联表
        CloudUserRole cloudUserRole = cloudUserRoleMapper.selectOne(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getUserId, cloudUser.getId()));
        CloudRole cloudRole = cloudRoleMapper.selectOne(new QueryWrapper<CloudRole>().lambda().eq(CloudRole::getId, cloudUserRole.getRoleId()));
        if(cloudRole!=null&&!cloudRole.equals("")){
            roles.add(cloudRole.getRoleEn());
            // 3.查询用户权限菜单
            String rId = cloudRole.getId();
            // 3.1 查询系统的所有菜单
            Set<String>  getAllMenus = cloudMenuMapper.getAllMenus(rId);
            // 3.2 判断如果是该登录用户的菜单的话，进行打钩，选中
            Set<String>  getPermission =  cloudMenuMapper.getPermission(rId);

            cloudUserInfoVo.setMenuList(getAllMenus);
            cloudUserInfoVo.setPermissionList(getPermission);
            return  cloudUserInfoVo;
        }


        return null;
    }


    @Override
    public CloudUserInfoVo getAuthorityByUserId(String userId) {
        // 1.查询用户信息
        CloudUser cloudUser = cloudUserMapper.selectOne(new QueryWrapper<CloudUser>().lambda().eq(CloudUser::getId, userId));
        CloudUserInfoVo cloudUserInfoVo = new CloudUserInfoVo();
        BeanUtils.copyProperties(cloudUser,cloudUserInfoVo);

        // 2.查询用户角色
        Set<String> roles = new HashSet<String>();
        // 查询useRole关联表
        CloudUserRole cloudUserRole = cloudUserRoleMapper.selectOne(new QueryWrapper<CloudUserRole>().lambda().eq(CloudUserRole::getUserId, cloudUser.getId()));
        CloudRole cloudRole = cloudRoleMapper.selectOne(new QueryWrapper<CloudRole>().lambda().eq(CloudRole::getId, cloudUserRole.getRoleId()));
        if(cloudRole!=null&&!cloudRole.equals("")){
            roles.add(cloudRole.getRoleEn());
            // 3.查询用户权限菜单
            String rId = cloudRole.getId();
            // 3.1 查询系统的所有菜单
            Set<String>  getAllMenus = cloudMenuMapper.getAllMenus(rId);
            // 3.2 判断如果是该登录用户的菜单的话，进行打钩，选中
            Set<String>  getPermission =  cloudMenuMapper.getPermission(rId);

            cloudUserInfoVo.setMenuList(getAllMenus);
            cloudUserInfoVo.setPermissionList(getPermission);
            return  cloudUserInfoVo;
        }


        return null;
    }

    @Override
    public CloudUser getUserByUserName(String accountName) {
        CloudUser cloudUser = cloudUserMapper.selectOne(new QueryWrapper<CloudUser>().lambda().eq(CloudUser::getAccountName, accountName));
        return cloudUser;
    }
}
