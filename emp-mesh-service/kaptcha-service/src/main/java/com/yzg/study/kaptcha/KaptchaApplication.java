package com.yzg.study.kaptcha;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = "com.yzg.study")
@ComponentScan(basePackages = {"com.yzg.study"})
@EnableSwagger2
@EnableKnife4j
public class KaptchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaptchaApplication.class, args);
    }


}
