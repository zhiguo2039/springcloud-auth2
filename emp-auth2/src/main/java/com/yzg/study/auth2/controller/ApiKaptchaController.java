package com.yzg.study.auth2.controller;




import com.yzg.study.api.feign.client.ApiKaptchaClient;
import com.yzg.study.common.vo.CloudResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Api(produces = "系统管理 - 验证码接口")
@RequestMapping("/rest/code/")
public class ApiKaptchaController {

    /**
     * 服务对象
     */
    @Autowired
    private ApiKaptchaClient apiKaptchaClient;
    /**
     * 验证码生成
     */
    @RequestMapping("/getKaptcha")
    public void getKaptcha(HttpServletResponse response, @RequestParam("type") String type) throws IOException {
        apiKaptchaClient.getKaptcha(response,type);
        //return  cloudResult;
    }


    @GetMapping(value = "/checkCode")
    @ApiOperation(value = "验证验证码", response = CloudResult.class)
    public CloudResult checkCode(@RequestParam("code") String code) {
        CloudResult cloudResult = apiKaptchaClient.checkCode(code);
        return  cloudResult;
    }

}