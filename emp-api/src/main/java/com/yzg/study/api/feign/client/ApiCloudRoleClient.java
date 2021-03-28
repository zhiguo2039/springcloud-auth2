package com.yzg.study.api.feign.client;

import com.alibaba.fastjson.JSONObject;
import com.yzg.study.api.feign.fallback.ApiCloudRoleFallBack;
import com.yzg.study.api.feign.config.DiyRequestInterceptorConfig;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * springcloud的feign组件进行远程服务调用和内部的负载均衡
 */

@FeignClient(contextId = "apiCloudRoleClient",name = "user-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiCloudRoleFallBack.class)
public interface ApiCloudRoleClient {

    @RequestMapping("/rest/role/list")
    public CloudResult list();
    @RequestMapping("/rest/role/commonList")
    public CloudResult getList();
    @RequestMapping("/rest/role/save")
    public CloudResult save(@RequestBody JSONObject jsonObject);
    @RequestMapping("/rest/role/update")
    public CloudResult update(@RequestBody JSONObject jsonObject);
    @RequestMapping("/rest/role/delete")
    public CloudResult delete(@RequestParam("roleId") String roleId);
    @RequestMapping("/rest/role/getAllMenus")
    public CloudResult getAllMenus();
    @PostMapping("/rest/role/setMenus")
    public CloudResult setMenus(@RequestBody JSONObject jsonObject);
}
