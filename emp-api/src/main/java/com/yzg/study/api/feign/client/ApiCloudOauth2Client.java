package com.yzg.study.api.feign.client;



import com.yzg.study.api.feign.fallback.ApiCloudOauth2FallBack;
import com.yzg.study.api.feign.config.DiyRequestInterceptorConfig;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "apiCloudOauth2Client",name = "user-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiCloudOauth2FallBack.class)
public interface ApiCloudOauth2Client {

    @RequestMapping("rest/oauth2/api/getUserById")
    CloudResult getAuthorityByUserId(@RequestParam("userId") String userId);

    @RequestMapping("rest/oauth2/api/info")
    CloudResult getOauthClientDetailsByClientId(@RequestParam("clientId") String clientId);


}
