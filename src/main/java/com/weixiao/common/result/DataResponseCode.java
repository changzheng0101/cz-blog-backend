package com.weixiao.common.result;

import lombok.Getter;

@Getter
public enum DataResponseCode {

    SUCCESS("200", "success"),
    BUSINESS_ERROR("400", "业务异常"),
    PARAM_ERROR("400", "参数错误"),
    SYSTEM_ERROR("500", "系统内部错误");

    private final String code;
    private final String msg;

    DataResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}