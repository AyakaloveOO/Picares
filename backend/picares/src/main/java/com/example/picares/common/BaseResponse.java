package com.example.picares.common;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private int code;
    private T data;
    private String message;

    /**
     * 成功，或失败自定义异常码
     * @param code
     * @param data
     * @param message
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 失败，errorcode
     * @param errorCode
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage());
    }

    /**
     * 失败，errorcode，自定义信息
     * @param errorCode
     * @param message
     */
    public BaseResponse(ErrorCode errorCode, String message) {
        this(errorCode.getCode(),null,message);
    }
}
