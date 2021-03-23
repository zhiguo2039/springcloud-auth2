package com.yzg.study.common.vo;


import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.entity.CloudUser;
import lombok.Data;

import java.util.List;

@Data
public class CloudRoleVo extends CloudRole {
    private List<CloudUser> cloudUserList;
    private List<CloudRoleMenusVo> yunRoleMenus;
    private List<CloudMenuVo> yunMenusVo;
    private List<String> checkedIds;
}
