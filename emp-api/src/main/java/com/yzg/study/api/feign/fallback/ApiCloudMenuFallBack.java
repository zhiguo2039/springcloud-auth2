package com.yzg.study.api.feign.fallback;


import com.yzg.study.api.feign.client.ApiCloudMenuClient;
import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ApiCloudMenuFallBack implements ApiCloudMenuClient {
    @Override
    public CloudResult list() {
        return null;
    }

    @Override
    public CloudResult save(CloudMenu cloudMenu) {
        return null;
    }

    @Override
    public CloudResult update(CloudMenu cloudMenu) {
        return null;
    }

    @Override
    public CloudResult delete(List<String> idList) {
        return null;
    }
}
