package com.yzg.study.security.properties;

import lombok.Data;

/**
 * 获取对应nacos的配置
 *
 * gaoxinqimeng:
 *   security:
 *     oauth2:
 *       clients[0]:
 *         accessTokenValidatySeconds: 21600
 *         refreshTokenValiditySeconds: 2592000
 *
 */

@Data
public class WebProperties {

    private String loginPage;

    private String[] unInterceptUris = {};
}
