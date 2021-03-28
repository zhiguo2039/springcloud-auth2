package com.yzg.study.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudUserRole;
import com.yzg.study.user.mapper.CloudUserRoleMapper;
import com.yzg.study.user.service.CloudUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class CloudUserRoleImpl  extends ServiceImpl<CloudUserRoleMapper, CloudUserRole> implements CloudUserRoleService {
}
