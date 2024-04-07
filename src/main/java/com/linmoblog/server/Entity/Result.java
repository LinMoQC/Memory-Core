package com.linmoblog.server.Entity;

import com.linmoblog.server.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    public Result(int code ,String message) {
        this.code =code;
        this.message = message;
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }


    public static Result<Void> success() {
        return new Result<Void>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS, data);
    }
    public static <T> Result<T> error() {
        return new Result<T>(ResultCode.FAILED);
    }
    public static <T> Result<T> error(String msg) {
        return new Result<T>(ResultCode.FAILED.getCode(), msg);
    }
}
