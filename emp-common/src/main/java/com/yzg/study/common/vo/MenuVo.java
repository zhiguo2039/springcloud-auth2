package com.yzg.study.common.vo;

import com.yzg.study.common.entity.CloudMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  菜单
 */
@Data
public class MenuVo extends CloudMenu implements Serializable {



    List<MenuVo> children = new ArrayList<>();

}
