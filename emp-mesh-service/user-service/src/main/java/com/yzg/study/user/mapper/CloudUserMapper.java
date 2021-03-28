package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudUser;

public interface CloudUserMapper  extends BaseMapper<CloudUser> {
    int deleteByPrimaryKey(String id);

    int insert(CloudUser record);

    int insertSelective(CloudUser record);

    CloudUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudUser record);

    int updateByPrimaryKey(CloudUser record);
}