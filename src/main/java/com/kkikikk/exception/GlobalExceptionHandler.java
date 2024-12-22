package com.kkikikk.exception;

import com.kkikikk.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器类，用于集中处理系统中抛出的异常
 * 通过@RestControllerAdvice注解标识，使该类成为一个全局的异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获并处理所有Exception类型的异常
     * 该方法会被Spring框架扫描到，作为全局的异常处理方法
     *
     * @param e 异常对象，由Spring捕获到并传递给该方法
     * @return 返回一个Result对象，包含错误信息
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace(); // 打印异常的堆栈信息，便于调试
        // 返回一个错误结果，如果异常信息为空，则显示默认的错误信息
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败~");
    }
}

