package com.yzg.study.common.entity;

import java.util.Date;

public class CloudRole {
    private String id;

    private String roleEn;

    private String roleCn;

    private Date createTime;

    private Date updateTime;

    private String describeText;

    private String isEnable;

    private String readonly;

    private String createUser;

    private String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleEn() {
        return roleEn;
    }

    public void setRoleEn(String roleEn) {
        this.roleEn = roleEn == null ? null : roleEn.trim();
    }

    public String getRoleCn() {
        return roleCn;
    }

    public void setRoleCn(String roleCn) {
        this.roleCn = roleCn == null ? null : roleCn.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescribeText() {
        return describeText;
    }

    public void setDescribeText(String describeText) {
        this.describeText = describeText == null ? null : describeText.trim();
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly == null ? null : readonly.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}