package com.linmoblog.server.exception;


import com.linmoblog.server.enums.ResultCode;

/**
 * 自定义异常
 * 目的： 统一的处理异常信息
 * 便于解耦、拦截器、service、controller等异常信息的结构
 * 在 service 中可以直接抛出异常，不会返回前端需要的结构
 *
 * @author 王开琦
 * @since 2024-04-07
 */
public class CommonException extends RuntimeException {
    private int code;
    private String msg;

    public CommonException(ResultCode resultCode) {
        super("异常状态码为：" + resultCode.getCode() + "；具体异常信息为：" + resultCode.getMessage());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public CommonException(int code, String msg) {
        super("异常状态码为：" + code + "；具体异常信息为：" + msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonException() {
        this(ResultCode.SUCCESS);
    }


    /**
     * of 方法，用于传入自定义信息
     */
    public static CommonException of(int code, String msg) {
        return new CommonException(code, msg);
    }

    /**
     * 静态方法：用于抛出异常
     */
    public static void thr(ResultCode responseStatusEnum) {
        throw new CommonException(responseStatusEnum);
    }
}
