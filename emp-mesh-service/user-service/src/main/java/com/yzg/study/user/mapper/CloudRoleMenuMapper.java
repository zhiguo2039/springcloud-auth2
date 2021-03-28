package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudRoleMenu;


public interface CloudRoleMenuMapper  extends BaseMapper<CloudRoleMenu>  {
    int deleteByPrimaryKey(String id);

    int insert(CloudRoleMenu record);

    int insertSelective(CloudRoleMenu record);

    CloudRoleMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudRoleMenu record);

    int updateByPrimaryKey(CloudRoleMenu record);
}