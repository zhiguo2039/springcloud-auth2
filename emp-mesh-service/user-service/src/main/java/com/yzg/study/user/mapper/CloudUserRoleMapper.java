package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudUserRole;

public interface CloudUserRoleMapper extends BaseMapper<CloudUserRole>  {
    int deleteByPrimaryKey(String id);

    int insert(CloudUserRole record);

    int insertSelective(CloudUserRole record);

    CloudUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudUserRole record);

    int updateByPrimaryKey(CloudUserRole record);
}