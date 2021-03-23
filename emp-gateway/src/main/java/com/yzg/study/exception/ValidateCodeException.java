package com.yzg.study.exception;

import lombok.Data;


@Data
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = -7285211528095468156L;

    private Integer code;

    private String msg;

    public ValidateCodeException(String msg) {
        super(msg);
        this.code = -1;
        this.msg = msg;
    }

    public ValidateCodeException() {
    }
}
