package com.yzg.study.api.feign.client;

import com.yzg.study.api.feign.fallback.ApiCloudHistoryFallBack;
import com.yzg.study.common.config.feign.DiyRequestInterceptorConfig;
import com.yzg.study.common.entity.CloudHistory;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "apiCloudHistoryClient",name = "gaoxinqimeng-user-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiCloudHistoryFallBack.class)
public interface ApiCloudHistoryClient {

    @PostMapping("rest/history/save")
    public CloudResult insert(@RequestBody CloudHistory cloudHistory);

    @RequestMapping("rest/history/list")
    public CloudResult page(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize);
}
