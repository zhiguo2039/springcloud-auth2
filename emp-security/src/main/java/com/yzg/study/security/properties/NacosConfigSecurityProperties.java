package com.yzg.study.security.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "gaoxinqimeng.security")
public class NacosConfigSecurityProperties {

    WebProperties web = new WebProperties();

    OAuth2Properties oauth2 = new OAuth2Properties();

}
