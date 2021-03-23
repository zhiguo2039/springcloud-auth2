package com.yzg.study.auth2.controller;


import com.yzg.study.common.vo.CloudResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class ApiOauthController {
    /**
     * 验证码识别
     */
    private final String DEFAULT_CODE_KEY = "yanzhengma";
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/token")
    public CloudResult getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
            TokenEndpoint tokenEndpoint = new TokenEndpoint();
            return custom(tokenEndpoint.getAccessToken(principal, parameters).getBody());


    }

    @PostMapping("/token")
    public CloudResult postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        TokenEndpoint tokenEndpoint = new TokenEndpoint();
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    //自定义返回格式
    private CloudResult custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap(token.getAdditionalInformation());
        data.put("accessToken", token.getValue());
        if (token.getRefreshToken() != null) {
            data.put("refreshToken", token.getRefreshToken().getValue());
        }
        data.put("expiration",token.getExpiration());
        data.put("defaultOAuth2AccessToken",token);
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(200);
        cloudResult.setMsg("登录成功！");
        cloudResult.setData(data);
        return cloudResult;
    }

}