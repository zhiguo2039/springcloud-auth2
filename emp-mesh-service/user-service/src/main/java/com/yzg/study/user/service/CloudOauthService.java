package com.yzg.study.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzg.study.common.entity.CloudOauth;

public interface CloudOauthService extends IService<CloudOauth> {
    CloudOauth getOauthClientDetailsByClientId(String clientId);
}
