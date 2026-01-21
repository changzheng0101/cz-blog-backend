package com.weixiao.common.exception;

import com.weixiao.common.result.DataResponseCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    
    private final DataResponseCode responseCode;
    
    public GlobalException(DataResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }
    
    public GlobalException(DataResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
    
    public GlobalException(DataResponseCode responseCode, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
    }
}