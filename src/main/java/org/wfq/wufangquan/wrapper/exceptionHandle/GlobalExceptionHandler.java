package org.wfq.wufangquan.wrapper.exceptionHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        e.printStackTrace();
        // 打印错误日志，便于排查
        log.error("未处理异常", e);

        // 返回统一失败响应
        return ApiResponse.fail(500, "服务器内部异常，请联系管理员");
    }
}