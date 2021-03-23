package com.yzg.study.security.properties;

import lombok.Data;


@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValidatySeconds;

    private int refreshTokenValiditySeconds;

}
