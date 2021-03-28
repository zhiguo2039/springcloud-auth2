package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudOauth;
import org.apache.ibatis.annotations.Select;

public interface CloudOauthMapper extends BaseMapper<CloudOauth>  {
    int deleteByPrimaryKey(String clientId);

    int insert(CloudOauth record);

    int insertSelective(CloudOauth record);

    CloudOauth selectByPrimaryKey(String clientId);

    int updateByPrimaryKeySelective(CloudOauth record);

    int updateByPrimaryKeyWithBLOBs(CloudOauth record);

    int updateByPrimaryKey(CloudOauth record);

    @Select("SELECT * FROM cloud_oauth WHERE client_id=#{0}")
    CloudOauth getOauthClientDetailsByClientId(String clientId);
}