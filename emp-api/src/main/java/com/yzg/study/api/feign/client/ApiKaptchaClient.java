package com.yzg.study.api.feign.client;

import com.yzg.study.api.feign.fallback.ApiKaptchaFallback;
import com.yzg.study.common.config.feign.DiyRequestInterceptorConfig;
import com.yzg.study.common.vo.CloudResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@FeignClient(contextId = "apiKaptchaClient",name = "kaptcha-service-mesh",configuration = DiyRequestInterceptorConfig.class,fallback = ApiKaptchaFallback.class)
public interface ApiKaptchaClient {

    /**
     * 获取验证码
     */
    @RequestMapping("/rest/code/getKaptcha")
    public void getKaptcha(HttpServletResponse httpServletResponse, @RequestParam("imageStyle") String imageStyle);
    /**
     * 验证验证码
     */
    @RequestMapping("/checkCode")
    public CloudResult checkCode(@RequestParam("code") String code);
}
