package com.linmoblog.server.exception;

import com.linmoblog.server.Entity.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 封装业务异常，可以在 service 层直接抛出此异常，系统会自动处理
     *
     * @param ex 异常信息
     * @return 封装后的异常信息
     */
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 可选：设置HTTP响应状态码
    public Result<Void> handleCommonException(Exception ex) {
        logger.error("业务异常", ex);
        return Result.error("业务异常" + ex.getMessage());
    }

    /**
     * 统一处理 JWT 相关的异常
     *
     * @param ex 异常信息
     * @return 封装后的异常信息
     */
    @ExceptionHandler({SignatureException.class, ExpiredJwtException.class, UnsupportedJwtException.class, MalformedJwtException.class, io.jsonwebtoken.security.SignatureException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // JWT异常通常对应401 Unauthorized
    public Result<Void> returnJwtException(Exception ex) {
        logger.error("JWT异常", ex);
        return Result.error(ex.getMessage());
    }

    /**
     * 封装系统异常
     *
     * @param ex 异常信息
     * @return 封装后的异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 默认处理未知异常，通常对应500 Internal Server Error
    public Result<Void> handleException(Exception ex) {
   logger.error("系统异常", ex);
        return Result.error("系统异常" + ex.getMessage());
    }
}
