package com.lon.uesrmanagerment.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误码
 */
@AllArgsConstructor
public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误",""),
    PARAMS_NULL_ERROR(40001,"请求数据为空",""),
    NO_AUTH(40101,"无权限",""),
    NO_LOGIN(40100,"未登录",""),
    SYSTEM_ERROR(50000,"系统内部异常","");
    private final int code;
    private final String message;
    private final String description;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
