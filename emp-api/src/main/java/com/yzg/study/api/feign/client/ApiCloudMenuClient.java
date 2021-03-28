package com.yzg.study.api.feign.client;


import com.yzg.study.api.feign.fallback.ApiCloudMenuFallBack;
import com.yzg.study.api.feign.config.DiyRequestInterceptorConfig;
import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * springcloud的feign组件进行远程服务调用和内部的负载均衡
 */

@FeignClient(contextId = "apiCloudMenuClient",name = "user-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiCloudMenuFallBack.class)
public interface ApiCloudMenuClient {

    @RequestMapping("/rest/menus/list")
    public CloudResult list();
    @PostMapping("/rest/menus/save")
    public  CloudResult save(@RequestBody CloudMenu cloudMenu);
    @PostMapping("/rest/menus/update")
    public  CloudResult update(@RequestBody CloudMenu cloudMenu);
    @RequestMapping("/rest/menus/delete")
    public  CloudResult delete(@RequestBody List<String> idList);
    }
