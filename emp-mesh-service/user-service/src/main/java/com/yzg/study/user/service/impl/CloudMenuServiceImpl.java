package com.yzg.study.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzg.study.common.entity.CloudMenu;
import com.yzg.study.common.vo.CloudMenuVo;
import com.yzg.study.user.mapper.CloudMenuMapper;
import com.yzg.study.user.service.CloudMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Array老师
 */
@Service
public class CloudMenuServiceImpl  extends ServiceImpl<CloudMenuMapper, CloudMenu> implements CloudMenuService {
    @Autowired
    private  CloudMenuMapper cloudMenuMapper;

    @Override
    public Map getMenusList() {
      //树形结构
        //1.查询所有根节点下面的儿子节点
        List<CloudMenuVo> cloudMenuByPId =   cloudMenuMapper.getMenuListByPId("0");
        // 2.查询所有的节点
        List<CloudMenuVo> allMenusList = cloudMenuMapper.getList();

        for (CloudMenuVo cloudMenuVo:cloudMenuByPId) {

            List<CloudMenuVo> children = new ArrayList<>();

            for(CloudMenuVo cloudMenuVo1:allMenusList) {
                if(cloudMenuVo1.getParentId().equals(cloudMenuVo.getId()))
                children.add(cloudMenuVo1);
            }

            cloudMenuVo.setChildren(children);
        }
        //3.把含有树形结构的整合后的数据返回给前端
        Map map = new HashMap();
        map.put("menuTree",cloudMenuByPId);
        return map;
    }
}
