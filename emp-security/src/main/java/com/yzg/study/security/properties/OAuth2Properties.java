package com.yzg.study.security.properties;


import lombok.Data;

/**
 * 获取对应nacos的配置
 *gaoxinqimeng:
 *  security:
 *    oauth2:
 *      clients[0]:
 *        accessTokenValidatySeconds: 21600
 *        refreshTokenValiditySeconds: 2592000
 *    web:
 *      unInterceptUris: /v2/api-docs,/swagger-ui.html,/swagger-resources/**,/webjars/**,/oauth/**,/actuator/**,/druid/*
 */
@Data
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

}
