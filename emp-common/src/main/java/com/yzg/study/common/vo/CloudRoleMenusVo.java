package com.yzg.study.common.vo;

import com.yzg.study.common.entity.CloudRoleMenu;
import lombok.Data;

import java.util.List;

@Data
public class CloudRoleMenusVo extends CloudRoleMenu {
    private List<CloudMenuVo> yunMenus;

    private  List<String> children;
}
