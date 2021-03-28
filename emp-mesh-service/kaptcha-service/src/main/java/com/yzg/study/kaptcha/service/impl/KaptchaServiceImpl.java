package com.yzg.study.kaptcha.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.yzg.study.common.vo.CloudResult;
import com.yzg.study.kaptcha.service.KaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class KaptchaServiceImpl implements KaptchaService {
    //验证码存入redis缓存
     @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void getKaptcha(HttpServletResponse response, String imageStyle) {

        //生成图片
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        LineCaptcha lineCaptcha = null;
        CircleCaptcha circleCaptcha = null;
        ShearCaptcha shearCaptcha = null;
        String code = null;
        if (imageStyle.equals("1")) {
            //生成线段干扰图形验证码
            lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        }
        if (imageStyle.equals("2")) {
            //生成圆圈干扰
            circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        }
        if (imageStyle.equals("3")) {
            //生成扭曲干扰图形验证码
            shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        }
        //获取图片流
        BufferedImage image = null;
        if (lineCaptcha != null) {
            code = lineCaptcha.getCode();
            image = lineCaptcha.getImage();
        }
        if (circleCaptcha != null) {
            code = circleCaptcha.getCode();
            image = circleCaptcha.getImage();
        }
        if (shearCaptcha != null) {
            code = shearCaptcha.getCode();
            image = shearCaptcha.getImage();
        }

        redisTemplate.opsForValue().set("yanzhengma", code);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CloudResult checkCode(String code) {
        String msg="验证码初始化没有验证";
        int staus =0;
        String  res = (String) redisTemplate.opsForValue().get("yanzhengma");
        if(res==null){
            msg="验证码不能为空";
            staus=-1;
        }else if(code.equals(res)){
            msg="验证码正确";
            staus=1;
        }
        CloudResult cloudResult = new CloudResult();
        cloudResult.setStatus(staus);
        cloudResult.setMsg(msg);
        cloudResult.setData(res);
        return cloudResult;
    }


}
