package com.yzg.study.common.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
public class DiyRequestInterceptorConfig implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DiyRequestInterceptorConfig.class);

    private final String AUTHORIZATION_HEADER = "Authorization";
    @Value("${gateway.url}")
    private String url;


    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            request.getRequestURI();
            //清洗日志请求头信息
            if (request != null) {
                // 只携带token
                String authorization = request.getHeader(AUTHORIZATION_HEADER);
                log.info("Authorization :\t\t" + authorization);
                requestTemplate.header(AUTHORIZATION_HEADER, authorization);

            }
        }
    }
}