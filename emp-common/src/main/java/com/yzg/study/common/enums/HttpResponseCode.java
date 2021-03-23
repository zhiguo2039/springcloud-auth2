package com.yzg.study.common.enums;

import lombok.Getter;


@Getter
public enum HttpResponseCode {

    /**
     * 成功
     * */
    SUCCESS(200,"成功"),
    /**
     * 未找到
     * */
    NOT_FOUND(404,"未找到 "),
    /**
     * 认证失败
     * */
    INVALID_TOKEN(401,"身份认证失败"),
    /**
     * 权限问题，例如不足
     * */
    UNAUTHORIZED(403,"权限不足"),
    /**
     * 服务器异常，一般空指针或者接口异常
     * */
    SERVER_ERROR(500,"服务器异常"),
    /**
     * 失败
     * */
    COMMON_FAIL(-1,"失败"),
    /**
     * 该client_id不存在
     */
    CLIENT_ERROR(402, "client_id去宇宙了，请检查正确否！"),
    /**
     * 服务器内部问题
     */
    SERVER_FUGUE(-100, "服务器内部调用问题，请检查接口或者重新发布启动"),

    /**
     * 请求超时，请稍后再试！
     */
    SERVER_TIMEOUT(504, "服务网络超时，请稍后尝试！"),

    /**
     * 服务调用失败
     * */
    HYSTRIX_ERROR(408, "调用xxx服务{}方法失败!");

    /**
     * 状态码
     * */
    private Integer code;
    /**
     * 消息
     * */
    private String message;

    HttpResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public static HttpResponseCode getEnum(int code) {
        for (HttpResponseCode ele : HttpResponseCode.values()) {
            if (ele.getCode() == code) {
                return ele;
            }
        }
        return null;
    }
}
