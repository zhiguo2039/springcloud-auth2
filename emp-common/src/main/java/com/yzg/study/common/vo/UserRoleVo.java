package com.yzg.study.common.vo;

import lombok.Data;

/**
 * 系统管理 - 用户角色关联表 查询参数
 */
@Data
public class UserRoleVo {

    private Long id;

    private Long roleId;

    private String userIds;
}
