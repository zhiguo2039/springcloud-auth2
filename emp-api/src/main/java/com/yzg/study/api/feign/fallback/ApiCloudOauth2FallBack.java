package com.yzg.study.api.feign.fallback;


import com.yzg.study.api.feign.client.ApiCloudOauth2Client;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.stereotype.Component;

/**
 *
 * 可以根据自己的业务进行调用失败的处理，或者进行熔断的处理，定义业务规则
 */
@Component
public class ApiCloudOauth2FallBack implements ApiCloudOauth2Client {


    @Override
    public CloudResult getAuthorityByUserId(String userId) {
        return null;
    }

    @Override
    public CloudResult getOauthClientDetailsByClientId(String clientId) {
        return null;
    }
}
