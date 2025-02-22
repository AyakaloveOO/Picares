package com.example.picares.common;

public class ResultUtils {
    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * 失败，自定义异常码
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse<?> error(int code, String message){
        return new BaseResponse<>(code,null,message);
    }

    /**
     * 失败，errorcode，
     * @param errorCode
     * @return
     */
    public static BaseResponse<?> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败，errorcode，自定义信息
     * @param errorCode
     * @param message
     * @return
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message){
        return new BaseResponse<>(errorCode,message);
    }
}
