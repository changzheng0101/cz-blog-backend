package com.weixiao.common.exception;

import com.weixiao.common.result.DataResponse;
import com.weixiao.common.result.DataResponseCode;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResponse<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        DataResponse<Map<String, String>> response = new DataResponse<>();
        response.setCode(DataResponseCode.PARAM_ERROR.getCode());
        response.setMsg("参数校验失败");
        response.setData(errors);
        
        return response;
    }

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public DataResponse<Map<String, String>> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        DataResponse<Map<String, String>> response = new DataResponse<>();
        response.setCode(DataResponseCode.PARAM_ERROR.getCode());
        response.setMsg("参数绑定失败");
        response.setData(errors);
        
        return response;
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(GlobalException.class)
    public DataResponse<Void> handleGlobalException(GlobalException ex) {
        return DataResponse.<Void>error(ex.getMessage());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public DataResponse<Void> handleException(Exception ex) {
        return DataResponse.<Void>error("系统内部错误: " + ex.getMessage());
    }
}