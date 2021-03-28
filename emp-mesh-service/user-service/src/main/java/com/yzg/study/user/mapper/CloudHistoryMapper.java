package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudHistory;


public interface CloudHistoryMapper extends BaseMapper<CloudHistory> {
    int deleteByPrimaryKey(String id);

    int insert(CloudHistory record);

    int insertSelective(CloudHistory record);

    CloudHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudHistory record);

    int updateByPrimaryKey(CloudHistory record);
}