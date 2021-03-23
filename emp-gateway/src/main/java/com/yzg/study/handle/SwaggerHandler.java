package com.yzg.study.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * gateway是一个用netty框架, zuul做过路由转发 zuul是用的路由页面跳转的方式，这种方式得在serlvet的方式下才可以进行
 * 由于gateway上用的是webflux，swagger是无法获取到这些信息的，那么我们只能自己伪造一个这些接口并返回成功的空信息，以此绕过报错，然后具体资源信息通过转发的请求各个微服务中获取
 */
@RestController
public class SwaggerHandler {
    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;
    @Autowired(required = false)
    private UiConfiguration uiConfiguration;
    private final SwaggerResourcesProvider swaggerResources;

    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
        this.swaggerResources = swaggerResources;
    }


    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }

    @GetMapping("/")
    public Mono<ResponseEntity> swaggerResourcesN() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
    @GetMapping("/csrf")
    public Mono<ResponseEntity> swaggerResourcesCsrf() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
    @GetMapping("/robots.txt")
    public Mono<ResponseEntity> swaggerResourcesRobots() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
    @GetMapping("/manifest.json")
    public Mono<ResponseEntity> swaggerResourcesManifest() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
    @GetMapping("/index.html")
    public Mono<ResponseEntity> swaggerResourcesIndex() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
}
