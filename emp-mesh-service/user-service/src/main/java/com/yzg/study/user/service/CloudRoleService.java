package com.yzg.study.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.vo.CloudMenuVo;

import java.util.List;
import java.util.Map;

public interface CloudRoleService extends IService<CloudRole> {
    Map getRoleAndUserList();

    List<CloudMenuVo> getAllMenusByELTree();

    int setMenus(JSONObject jsonObject);
}
