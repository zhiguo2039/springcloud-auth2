package com.yzg.study.api.feign.fallback;


import com.yzg.study.api.feign.client.ApiCloudUserClient;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserVo;
import org.springframework.stereotype.Component;

/**
 *
 * 可以根据自己的业务进行调用失败的处理，或者进行熔断的处理，定义业务规则
 */
@Component
public class ApiCloudUserFallBack implements ApiCloudUserClient {

    @Override
    public CloudResult getList() {
        return null;
    }

    @Override
    public CloudResult page(int pageNum, int pageSize, String accountName) {
        return null;
    }

    @Override
    public CloudResult save(CloudUserVo cloudUservo) {
        return null;
    }

    @Override
    public CloudResult update(CloudUserVo cloudUservo) {
        return null;
    }

    @Override
    public CloudResult delete(CloudUserVo cloudUservo) {
        return null;
    }

    @Override
    public CloudResult getInfo(String userName) {
        return null;
    }

    @Override
    public CloudResult getUserByUserName(String accountName) {
        return null;
    }
}
