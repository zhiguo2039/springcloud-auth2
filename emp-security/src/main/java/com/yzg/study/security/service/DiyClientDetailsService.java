package com.yzg.study.security.service;

import com.alibaba.fastjson.JSON;

import com.yzg.study.api.feign.client.ApiCloudOauth2Client;
import com.yzg.study.common.entity.CloudOauth;
import com.yzg.study.common.enums.HttpResponseCode;
import com.yzg.study.common.exception.CommonException;
import com.yzg.study.common.vo.CloudResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 授权的clientId验证服务
 */

@Service
public class DiyClientDetailsService implements ClientDetailsService {

    private  static  final Logger log = LoggerFactory.getLogger(ClientDetailsService.class);

    @Autowired
    private ApiCloudOauth2Client clientOauth2Client;

    /**
     * http://gonglue.qinggl.com/app/time/nianlingjisuanqi.jsp  时分秒计算器，数据库控制过期，单位:s
     * @param clientId
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //鉴权
        CloudResult apiResponse = clientOauth2Client.getOauthClientDetailsByClientId(clientId);
        CloudOauth model = JSON.parseObject(apiResponse.getData().toString(), CloudOauth.class);
//        CloudOauth model = new CloudOauth();
//        if(apiResponse!=null) {
//            model = (CloudOauth) apiResponse.getData();
//        }
        if (model == null) {
            throw new CommonException(HttpResponseCode.CLIENT_ERROR);
        }
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(model.getClientId());
        if (StringUtils.isNotEmpty(model.getResourceIds())) {
            clientDetails.setResourceIds(Arrays.asList(model.getResourceIds().split(",")));
        }
        //密匙
        clientDetails.setClientSecret(new BCryptPasswordEncoder().encode(model.getClientSecret()));
        //授权类型
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(model.getAuthorizedGrantTypes().split(",")));
        //权限大小
        clientDetails.setScope(Arrays.asList(model.getScope().split(",")));
        //从数据库表中获取 token的过期时间和refresh_token的过期时间
        Integer accessTokenValidity = model.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            clientDetails.setAccessTokenValiditySeconds(accessTokenValidity);
        }
        Integer refreshTokenValidity = model.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            clientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity);
        }
        clientDetails.isAutoApprove(model.getAutoapprove());
        return clientDetails;
    }
}
