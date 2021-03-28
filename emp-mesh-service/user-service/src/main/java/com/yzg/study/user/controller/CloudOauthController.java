package com.yzg.study.user.controller;


import com.yzg.study.common.entity.CloudOauth;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.common.vo.CloudUserInfoVo;
import com.yzg.study.user.service.CloudOauthService;
import com.yzg.study.user.service.CloudUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对于client的数据库令牌授权
 */
@RequestMapping("rest/oauth2")
@RefreshScope
@RestController
@Api(value = "授权的client管理",tags = {"授权接口"})
public class CloudOauthController {

    @Autowired
    CloudUserService cloudUserService;

    @Autowired
    CloudOauthService cloudOauthService;

    @RequestMapping("/api/getUserById")
    @ApiOperation(value = "根据userId查询授权",produces = "根据userId查询授权",httpMethod = "GET")
    public CloudResult getAuthorityByUserId(@RequestParam("userId") String userId){
        CloudUserInfoVo cloudUserInfoVo =cloudUserService.getAuthorityByUserId(userId);

        CloudResult cloudResult =  new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("成功！");
        cloudResult.setData(cloudUserInfoVo);
        return  cloudResult;

    }


    @RequestMapping("/api/info")
    @ApiOperation(value = "根据clientId查询授权",produces = "根据clientId查询授权",httpMethod = "GET")
    public  CloudResult getOauthClientDetailsByClientId(@RequestParam("clientId") String clientId){

        CloudOauth cloudOauth = cloudOauthService.getOauthClientDetailsByClientId(clientId);
        CloudResult cloudResult =  new CloudResult();
        cloudResult.setStatus(1);
        cloudResult.setMsg("成功！");
        cloudResult.setData(cloudOauth);
        return  cloudResult;

    }


}
