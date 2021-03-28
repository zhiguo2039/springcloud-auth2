package com.yzg.study.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzg.study.common.entity.CloudMenu;

import java.util.Map;

public interface CloudMenuService extends IService<CloudMenu> {
    Map getMenusList();
}
