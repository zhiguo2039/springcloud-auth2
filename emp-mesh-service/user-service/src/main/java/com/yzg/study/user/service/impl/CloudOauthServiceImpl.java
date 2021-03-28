package com.yzg.study.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudOauth;
import com.yzg.study.user.mapper.CloudOauthMapper;
import com.yzg.study.user.service.CloudOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudOauthServiceImpl  extends ServiceImpl<CloudOauthMapper, CloudOauth> implements CloudOauthService {

    @Autowired
    CloudOauthMapper cloudOauthMapper;

    @Override
    public CloudOauth getOauthClientDetailsByClientId(String clientId) {
        CloudOauth  cloudOauth = cloudOauthMapper.getOauthClientDetailsByClientId(clientId);
        return cloudOauth;
    }
}
