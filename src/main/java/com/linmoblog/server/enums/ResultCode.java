package com.linmoblog.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : [TongJing]--------GitHub：<a href="https://github.com/defings">...</a>
 * @version : [v1.0]
 * @description : TODO 响应枚举
 * @createTime : [2024/4/6 14:26]
 * @updateUser : [TongJing]
 * @updateTime : [2024/4/6 14:26]
 * @updateRemark : [说明本次修改内容]
 */
@AllArgsConstructor
@Getter
public enum ResultCode {
    // 成功响应
    SUCCESS(200, "ok"),
    FAILED(500, "failed"),
    SUCCESS_UPLOAD(200, "上传成功"),
    SUCCESS_LOGIN(200, "Login successful"),
    SUCCESS_FILE_DEL(200, "文件删除成功"),

    // 失败响应
    ERROR_LOGIN(401, "Login failed"),
    ERROR_UPLOAD(508, "上传失败"),
    ERROR_FILE_DEL(509, "文件删除失败");
    private final int code;
    private final String message;
}
