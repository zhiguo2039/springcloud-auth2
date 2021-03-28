package com.yzg.study.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudMenuVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface CloudMenuMapper extends BaseMapper<CloudMenu> {
    int deleteByPrimaryKey(String id);

    int insert(CloudMenu record);

    int insertSelective(CloudMenu record);

    CloudMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudMenu record);

    int updateByPrimaryKey(CloudMenu record);

     @Select("SELECT   cloud_menu.`menu_code`   FROM cloud_menu , cloud_role_menu  WHERE cloud_menu.`id`=cloud_role_menu.`menu_id` AND  cloud_role_menu.`role_id`=#{0}")
    Set<String> getAllMenus(String rId);
     @Select("SELECT   cloud_menu.`menu_auth`   FROM cloud_menu , cloud_role_menu  WHERE cloud_menu.`id`=cloud_role_menu.`menu_id` AND  cloud_role_menu.`role_id`=#{0}")
    Set<String> getPermission(String rId);

     @Select("SELECT * FROM  `cloud_menu` WHERE parent_id=#{0}")
    List<CloudMenuVo> getMenuListByPId(String pId);

    @Select("SELECT * FROM   cloud_menu  ")
    List<CloudMenuVo> getList();

    @Select("SELECT  ym.id,ym.menu_name,ym.parent_id,ym.menu_code FROM cloud_menu ym,cloud_role_menu  yrm  " +
            "  WHERE  ym.id=yrm.menu_id AND  ym.parent_id=#{pId} AND yrm.role_id=#{rId} ORDER BY ym.id")
    List<CloudMenuVo> getMenuList(@Param("pId") String pId, @Param("rId") String rId);
}