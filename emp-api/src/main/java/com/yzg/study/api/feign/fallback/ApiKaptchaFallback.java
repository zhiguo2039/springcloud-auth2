package com.yzg.study.api.feign.fallback;

import com.yzg.study.api.feign.client.ApiKaptchaClient;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
@Component
public class ApiKaptchaFallback implements ApiKaptchaClient {
    @Override
    public void getKaptcha(HttpServletResponse httpServletResponse, String imageStyle) {

    }

    @Override
    public CloudResult checkCode(String code) {
        return null;
    }
}