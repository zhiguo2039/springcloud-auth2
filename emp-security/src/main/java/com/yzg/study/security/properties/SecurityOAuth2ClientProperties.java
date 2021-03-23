package com.yzg.study.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * nacos 配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "security.oauth2.client")
public class SecurityOAuth2ClientProperties {

    private String clientId;

    private String clientSecret;
}
