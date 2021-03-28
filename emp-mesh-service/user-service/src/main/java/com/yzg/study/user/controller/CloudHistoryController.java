package com.yzg.study.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzg.study.common.entity.CloudHistory;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.user.service.CloudHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 日志的插入和查询，日志特殊不能更新和删除
 */
@RestController
@RequestMapping("/rest/history")
@Api(value = "日志管理",tags = {"日志接口"})
public class CloudHistoryController {

    @Autowired
    private CloudHistoryService cloudHistoryService;


    /*
     * 日志表分页 ,pageNum ，pageSize
     */
    @RequestMapping("/list")
    @ApiOperation(value = "日志分页",produces = "日志分页",httpMethod = "GET")
    public CloudResult page(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        CloudResult cloudResult = new CloudResult();
        IPage<CloudHistory> iPage = new Page<>(pageNum, pageSize);
        IPage<CloudHistory> page = cloudHistoryService.page(iPage);

        cloudResult.setStatus(1);
        cloudResult.setMsg("分页查询成功");
        cloudResult.setData(page);
        return  cloudResult;

        }


    /**
     * 日志的添加
     */
     @PostMapping("/save")
     @ApiOperation(value = "日志添加",produces = "日志操作",httpMethod = "POST")
     public  CloudResult insert(@RequestBody CloudHistory cloudHistory){
         CloudResult cloudResult = new CloudResult();
         boolean save = cloudHistoryService.save(cloudHistory);
         cloudResult.setStatus(1);
         cloudResult.setMsg("插入成功");
         cloudResult.setData(save);
         return  cloudResult;

     }

}
