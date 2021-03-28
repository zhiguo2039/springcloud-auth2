package com.yzg.study.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudHistory;
import com.yzg.study.user.mapper.CloudHistoryMapper;
import com.yzg.study.user.service.CloudHistoryService;
import org.springframework.stereotype.Service;

@Service
public class CloudHistoryServiceImpl  extends ServiceImpl<CloudHistoryMapper, CloudHistory> implements CloudHistoryService {
}
