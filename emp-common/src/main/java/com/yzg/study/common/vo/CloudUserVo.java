package com.yzg.study.common.vo;

import com.yzg.study.common.entity.CloudRole;
import com.yzg.study.common.entity.CloudUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class CloudUserVo extends CloudUser implements Serializable {


    private CloudRole cloudRole;
    private  String roleId;
    private  String roleName;
}
