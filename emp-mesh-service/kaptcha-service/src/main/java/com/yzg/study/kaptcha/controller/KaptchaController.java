package com.yzg.study.kaptcha.controller;


import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.kaptcha.service.KaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码服务
 */
@RestController
@RequestMapping("/rest/code")
@Api(value = "验证管理",tags = {"验证接口"})
public class KaptchaController {

    @Autowired
    private KaptchaService kaptchaService;

    /**
     * 获取验证码
     */
    @RequestMapping("/getKaptcha")
    @ApiOperation(value = "验证码获取",produces = "验证码入口")
    public void getKaptcha(HttpServletResponse httpServletResponse,@RequestParam("imageStyle") String imageStyle){

        kaptchaService.getKaptcha(httpServletResponse,imageStyle);
    }

    /**
     * 验证验证码
     */
    @RequestMapping("/checkCode")
    @ApiOperation(value = "验证码验证",produces = "验证码入口")
    public CloudResult checkCode(@RequestParam("code") String code){

        CloudResult cloudResult = kaptchaService.checkCode(code);
        return cloudResult;
    }


}
