package com.yzg.study.kaptcha.service;


import com.yzg.study.common.vo.CloudResult;

import javax.servlet.http.HttpServletResponse;

public interface KaptchaService {
    void getKaptcha(HttpServletResponse httpServletResponse, String imageStyle);

    CloudResult checkCode(String code);
}
