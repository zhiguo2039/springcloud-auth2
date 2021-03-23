package com.yzg.study.springcloud.controller;

import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.springcloud.service.IKaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码服务
 */
@RestController
@RequestMapping("/rest/code")
@Api(value = "验证管理",tags = {"验证接口"})
public class KaptchaController {

    @Autowired
    private IKaptchaService kaptchaService;

    /**
     * 获取验证码
     */
    @GetMapping("/getKaptcha")
    @ApiOperation(value = "验证码获取",produces = "验证码入口")
    public void getKaptcha(HttpServletResponse httpServletResponse, @RequestParam("imageStyle") String imageStyle){

        kaptchaService.getKaptcha(httpServletResponse,imageStyle);
    }

    /**
     * 验证验证码
     */
    @PostMapping("/checkCode")
    @ApiOperation(value = "验证码验证",produces = "验证码入口")
    public CloudResult checkCode(@RequestParam("code") String code){

        CloudResult cloudResult = kaptchaService.checkCode(code);
        return cloudResult;
    }


}
