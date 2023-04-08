package com.liujin.internalcommon.dto;

import com.liujin.internalcommon.constant.CommonStatusEnum;
import com.liujin.internalcommon.response.TokenResponse;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) //链式调用
public class ResponseResult <T>{
    private Integer code;
    private String message;
    private T data;

    //成功
    public static <T> ResponseResult success(T data){
        return new ResponseResult()
                .setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue())
                .setData(data);
    }

    public static <T> ResponseResult success(){
        return new ResponseResult()
                .setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue());
    }
    public static  ResponseResult success( int code, String message, TokenResponse token ) {
        return new ResponseResult()
                .setCode(CommonStatusEnum.VERIFICATION_CODE_SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue())
                .setData(token);
    }

    /**
     * 失败：统一失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }
    /**
     * 失败：提示错误码和信息
     * @param code
     * @param massage
     * @return
     */
    public static ResponseResult fail(int code, String massage){
        return new ResponseResult()
                .setCode(CommonStatusEnum.FAIL.getCode())
                .setMessage(CommonStatusEnum.FAIL.getValue());
    }

    public static <T> ResponseResult fail(int code, String massage, T data){
        return new ResponseResult()
                .setCode(CommonStatusEnum.FAIL.getCode())
                .setMessage(CommonStatusEnum.FAIL.getValue())
                .setData(data);
    }


}
