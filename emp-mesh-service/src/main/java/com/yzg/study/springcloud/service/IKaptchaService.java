package com.yzg.study.springcloud.service;

import com.yzg.study.common.vo.CloudResult;

import javax.servlet.http.HttpServletResponse;

public interface IKaptchaService {
    void getKaptcha(HttpServletResponse httpServletResponse, String imageStyle);

    CloudResult checkCode(String code);
}
