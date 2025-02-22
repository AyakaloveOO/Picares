package com.example.picares.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    /**
     * 自定义异常码
     * @param code
     * @param message
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * errorcode
     * @param errorCode
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * errorcode，自定义信息
     * @param errorCode
     * @param message
     */
    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message);
    }
}
