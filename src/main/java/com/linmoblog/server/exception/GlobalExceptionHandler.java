package com.linmoblog.server.exception;

import com.linmoblog.server.Entity.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 封装业务异常，可以在 service 层直接抛出此异常，系统会自动处理
     *
     * @param ex 异常信息
     * @return 封装后的异常信息
     */
    @ExceptionHandler(CommonException.class)
    public Result<Void> handleCommonException(Exception ex) {
        ex.printStackTrace();
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
    public Result<Void> returnJwtException(SignatureException ex) {
        ex.printStackTrace();
        return Result.error(ex.getMessage());
    }

    /**
     * 封装系统异常
     *
     * @param ex 异常信息
     * @return 封装后的异常信息
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex) {
        ex.printStackTrace();
        return Result.error("系统异常" + ex.getMessage());
    }
}
