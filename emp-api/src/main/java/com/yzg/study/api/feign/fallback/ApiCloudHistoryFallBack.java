package com.yzg.study.api.feign.fallback;


import com.yzg.study.api.feign.client.ApiCloudHistoryClient;
import com.yzg.study.common.entity.CloudHistory;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.stereotype.Component;

@Component
public class ApiCloudHistoryFallBack implements ApiCloudHistoryClient {
    @Override
    public CloudResult insert(CloudHistory cloudHistory) {
        return null;
    }

    @Override
    public CloudResult page(int pageNum, int pageSize) {
        return null;
    }
}
