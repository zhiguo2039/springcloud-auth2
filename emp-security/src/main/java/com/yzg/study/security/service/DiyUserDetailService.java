package com.yzg.study.security.service;

import com.alibaba.fastjson.JSON;
import com.yzg.study.api.feign.client.ApiCloudOauth2Client;
import com.yzg.study.api.feign.client.ApiCloudUserClient;
import com.yzg.study.common.entity.CloudUser;
import com.yzg.study.common.exception.CommonException;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.yzg.study.security.model.SecurityUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 授权的用户名和密码验证服务
 */
@Component
public class DiyUserDetailService implements UserDetailsService {

    @Autowired
    private ApiCloudUserClient cloudUserClient;
    @Autowired
    private ApiCloudOauth2Client cloudOauth2Client;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(accountName)){
            throw new CommonException("登录名是会员的重要凭证，不能为空或者空格");
        }
        CloudResult cloudResult = cloudUserClient.getUserByUserName(accountName);
        CloudUser user = JSON.parseObject(cloudResult.getData().toString(), CloudUser.class);
        //CloudUser user = JSON.parseObject(JSON.toJSONString( cloudResult.getData(), true), CloudUser.class);
        if (user == null) {
            throw new CommonException("您输入登录名无法从数据库查询核验，请检查");
        } else if ("2".equals(user.getIsLock())) {
            throw new CommonException("用户违反社区规则，已禁用");
        }
        // 判断盗版的app的client_id等非法的场景，禁止调用，同时请同学们后续添加判断response ？ null
        CloudResult response = cloudOauth2Client.getAuthorityByUserId(user.getId());

        CloudUserInfoVo cloudUserInfoVo = JSON.parseObject(response.getData().toString(), CloudUserInfoVo.class);
        //CloudUserInfoVo   cloudUserInfoVo = (CloudUserInfoVo) response.getData();
        Set<String> authList = cloudUserInfoVo.getPermissionList();
        List<GrantedAuthority> lists = new ArrayList<>();
        if(authList != null && authList.size()>0){
            for (String auth : authList) {
                lists.add(new SimpleGrantedAuthority(auth));
            }
        }
        SecurityUser securityUser =  new SecurityUser();
        securityUser.setAccountName(user.getAccountName());
        securityUser.setId(user.getId());
        securityUser.setAuthorities(lists);
        securityUser.setPassWord(user.getPassWord());
        return securityUser;
    }
}
