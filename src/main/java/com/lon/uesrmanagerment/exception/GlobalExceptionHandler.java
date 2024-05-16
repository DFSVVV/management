package com.lon.uesrmanagerment.exception;

import com.lon.uesrmanagerment.common.BaseResponse;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.transform.Result;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException" + e.getMessage(),e);
        return ResultUtils.error(e);
    }
    public BaseResponse runtimeExceptionHandler(RuntimeException runtimeException){
        log.error("runtimeException",runtimeException);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);

    }
}
