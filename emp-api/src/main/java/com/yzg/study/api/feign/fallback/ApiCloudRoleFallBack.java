package com.yzg.study.api.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.yzg.study.api.feign.client.ApiCloudRoleClient;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.stereotype.Component;

@Component
public class ApiCloudRoleFallBack implements ApiCloudRoleClient {
    @Override
    public CloudResult list() {
        return null;
    }

    @Override
    public CloudResult getList() {
        return null;
    }

    @Override
    public CloudResult save(JSONObject jsonObject) {
        return null;
    }

    @Override
    public CloudResult update(JSONObject jsonObject) {
        return null;
    }

    @Override
    public CloudResult delete(String roleId) {
        return null;
    }

    @Override
    public CloudResult getAllMenus() {
        return null;
    }

    @Override
    public CloudResult setMenus(JSONObject jsonObject) {
        return null;
    }
}
