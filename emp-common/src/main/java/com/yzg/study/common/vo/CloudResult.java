package com.yzg.study.common.vo;

import com.alibaba.fastjson.JSONObject;
import com.yzg.study.common.enums.HttpResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * @description: 通用服务响应对象
 **/
//保证序列化json的时候,如果是null的对象,key也会消失
@Data
@NoArgsConstructor
public class CloudResult<T> implements Serializable {
    //1.定义属性
    private int status; //200

    private String msg; // 成功

    private T data;   // json格式的数据......

    /**
     * 调用服务的错误
     * @param serviceName 服务名
     * @param methodName 方法名
     * @return 结果视图
     */
    public static CloudResult hystrixError(String serviceName,String methodName) {
        String msg = HttpResponseCode.HYSTRIX_ERROR.getMessage().replace("xxx", serviceName).replace("{}", methodName);
        return new CloudResult(HttpResponseCode.HYSTRIX_ERROR.getCode(), msg);
    }

    //2.定义构造函数
    private CloudResult(int status) {
        this.status = status;
    }

    // status=200 ，data {"name":"张三"，“poststation”:"test"}
    private CloudResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private CloudResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    // status=200  msg="成功"
    private CloudResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


//
//    @JsonIgnore
//    //使之不在json序列化结果当中
//    //4.判断这个响应是不是一个正确的响应
//    public boolean isSuccess() {
//        return this.status == ResponseCode.SUCCESS.getCode();
//    }

    //5.定义返回对象的方法
    public static <T> CloudResult<T> createBySuccess() {
        return new CloudResult<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> CloudResult<T> createBySuccessMessage(String msg) {
        return new CloudResult<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> CloudResult<T> createBySuccess(T data) {
        return new CloudResult<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> CloudResult<T> createByCodeSuccess(int status, String msg, T data) {
        return new CloudResult<T>(status,msg, data);
    }

    public static <T> CloudResult<T> createBySuccess(String msg, T data) {
        return new CloudResult<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> CloudResult<T> createByError() {
        return new CloudResult<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> CloudResult<T> createByErrorMessage(String errorMessage) {
        return new CloudResult<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> CloudResult<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new CloudResult<T>(errorCode, errorMessage);
    }/**
     * 将request参数值转为json
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!isNullOrEmpty(missCol)) {
                jsonObject.clear();
                jsonObject.put("status", "400");
                jsonObject.put("msg", "缺少必填参数:" + missCol.trim());
                jsonObject.put("info", new JSONObject());

            }
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }

}