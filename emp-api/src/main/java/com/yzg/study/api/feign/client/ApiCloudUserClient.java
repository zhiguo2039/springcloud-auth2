package com.yzg.study.api.feign.client;


import com.yzg.study.api.feign.fallback.ApiCloudUserFallBack;
import com.yzg.study.api.feign.config.DiyRequestInterceptorConfig;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * springcloud的feign组件进行远程服务调用和内部的负载均衡
 */

@FeignClient(contextId = "apiCloudUserClient",name = "user-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiCloudUserFallBack.class)
public interface ApiCloudUserClient {

    //调用user的远程服务
    @RequestMapping("/rest/user/list")
    public CloudResult getList();

    @RequestMapping("/rest/user/page")
    public CloudResult page(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            @RequestParam(value = "accountName", required = false) String accountName);

    @PostMapping("/rest/user/save")
    public CloudResult  save(@RequestBody CloudUserVo cloudUservo);

    @PostMapping("/rest/user/update")
    public CloudResult  update(@RequestBody CloudUserVo cloudUservo);

    @RequestMapping("/rest/user/delete")
    public  CloudResult delete(@RequestBody CloudUserVo cloudUservo);

    @RequestMapping("/rest/user/getInfo")
    public  CloudResult getInfo(@RequestParam("userName") String userName) ;

    @RequestMapping("/rest/user/api")
    public  CloudResult getUserByUserName(@RequestParam("accountName") String accountName);


}
