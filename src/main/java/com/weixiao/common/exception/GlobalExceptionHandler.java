package com.weixiao.common.exception;

import com.weixiao.common.result.DataResponse;
import com.weixiao.common.result.DataResponseCode;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
     * 处理业务异常
     */
    @ExceptionHandler(GlobalException.class)
    public DataResponse<Void> handleGlobalException(GlobalException ex) {
        log.error("业务异常", ex);
        return DataResponse.error(ex.getMessage());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public DataResponse<Void> handleException(Exception ex) {
        log.error("全局异常处理器拦截", ex);
        return DataResponse.error("系统内部错误: " + ex.getMessage());
    }
}