package com.yzg.study.auth2.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yzg.study.api.feign.client.ApiCloudHistoryClient;
import com.yzg.study.common.entity.CloudHistory;
import com.yzg.study.common.vo.CloudResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统 - 日志表(CloudHistory)表控制层
 *
 * @author Array老师
 * @since 2020-03-29 19:56:29
 */
@RestController
@RequestMapping("/rest/history")
@Api(produces = "系统管理 - 日志表接口")
public class ApiCloudHistoryController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private ApiCloudHistoryClient apiCloudHistoryClient;





    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */

    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取系统管理 - 日志表列表分页", httpMethod = "POST", response = CloudResult.class)
    public CloudResult list(@RequestBody JSONObject jsonObject ) {
        if(jsonObject!=null&&!jsonObject.equals("")) {

            int pageNum = jsonObject.getInteger("pageNum");
            int pageSize = jsonObject.getInteger("pageRow");
            CloudResult cloudResult = apiCloudHistoryClient.page(pageNum,pageSize);

            return cloudResult;
        }

        return  CloudResult.createByError();

    }
    /**
     * 系统日志处理
     */
    @ApiOperation(value = "日志录入", httpMethod = "POST", response = CloudResult.class)
    @RequestMapping("/save")
    public  CloudResult insert(@RequestBody CloudHistory cloudHistory){
        CloudResult cloudResult = apiCloudHistoryClient.insert(cloudHistory);
        return cloudResult;
    }

}