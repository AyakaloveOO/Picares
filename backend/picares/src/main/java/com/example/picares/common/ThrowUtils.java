package com.example.picares.common;

public class ThrowUtils {

    /**
     * 抛出异常
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition,RuntimeException runtimeException) {
        if (condition){
            throw runtimeException;
        }
    }

    /**
     * 接收errorcode
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition,ErrorCode errorCode) {
        throwIf(condition,new BusinessException(errorCode));
    }

    /**
     * 接收errorcode，自定义信息
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition,ErrorCode errorCode,String message) {
        throwIf(condition,new BusinessException(errorCode,message));
    }
}
