package com.lon.uesrmanagerment.common;

import com.lon.uesrmanagerment.exception.BusinessException;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }
    public static <T>BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
    public static <T>BaseResponse<T> error(BusinessException e){
        return new BaseResponse<>(e.getCode(),null,e.getMessage(),e.getDescription());
    }
}
