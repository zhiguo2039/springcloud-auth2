package com.yzg.study.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzg.study.common.entity.CloudUser;
import com.yzg.study.common.vo.CloudUserInfoVo;
import com.yzg.study.common.vo.CloudUserVo;

/**
 * 用户管理，可以直接使用mybatisplus的封装方法
 */
public interface CloudUserService  extends IService<CloudUser> {

    int saveUserAndRole(CloudUserVo cloudUservo);

    int updateUserAndRole(CloudUserVo cloudUservo);

    CloudUserInfoVo getInfo(String userName);

    CloudUserInfoVo getAuthorityByUserId(String userId);

    CloudUser getUserByUserName(String accountName);
}
