package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.vo.CloudRoleVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CloudRoleMapper  extends BaseMapper<CloudRole> {
    int deleteByPrimaryKey(String id);

    int insert(CloudRole record);

    int insertSelective(CloudRole record);

    CloudRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudRole record);

    int updateByPrimaryKey(CloudRole record);

    @Select("SELECT *,id FROM  `cloud_role`")
    List<CloudRoleVo> getList();
}