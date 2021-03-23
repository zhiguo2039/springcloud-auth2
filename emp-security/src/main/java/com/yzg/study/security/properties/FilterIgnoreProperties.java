package com.yzg.study.security.properties;

import com.yzg.study.common.constants.GlobalConstant;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 忽略的url
 */
@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'ignore'.isEmpty()")
@ConfigurationProperties(prefix = GlobalConstant.FILTER_IGNORE)
public class FilterIgnoreProperties {
	private List<String> clients = new ArrayList<>();
	private List<String> urls = new ArrayList<>();
	private List<String> swaggerProviders = new ArrayList<>();
}
