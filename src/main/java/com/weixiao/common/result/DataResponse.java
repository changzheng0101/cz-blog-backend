package com.weixiao.common.result;

import lombok.Data;

@Data
public class DataResponse<T> {

    private String code;
    private String msg;
    private T data;

    public DataResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> DataResponse<T> success(T data) {
        return new DataResponse<>(DataResponseCode.SUCCESS.getCode(), DataResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> DataResponse<T> success() {
        return success(null);
    }

    public static <T> DataResponse<T> error(DataResponseCode responseCode) {
        return new DataResponse<>(responseCode.getCode(), responseCode.getMsg(), null);
    }

    public static <T> DataResponse<T> error(String msg) {
        return new DataResponse<>(DataResponseCode.SYSTEM_ERROR.getCode(), msg, null);
    }
}