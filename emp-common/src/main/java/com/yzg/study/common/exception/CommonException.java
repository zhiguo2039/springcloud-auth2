package com.yzg.study.common.exception;


import com.yzg.study.common.enums.HttpResponseCode;

public class CommonException extends RuntimeException {

    public Integer code;

    public String msg;

    public CommonException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonException(String msg) {
        super(msg);
        this.code = HttpResponseCode.COMMON_FAIL.getCode();
        this.msg = msg;
    }

    public CommonException() {
        super(HttpResponseCode.COMMON_FAIL.getMessage());
        this.code = HttpResponseCode.COMMON_FAIL.getCode();
        this.msg = HttpResponseCode.COMMON_FAIL.getMessage();
    }
    public CommonException(HttpResponseCode status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.msg = status.getMessage();
    }

    public CommonException(Throwable cause) {
        super(cause);
    }


}
