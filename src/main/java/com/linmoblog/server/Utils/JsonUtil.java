package com.linmoblog.server.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : [TongJing]--------GitHub：<a href="https://github.com/defings">...</a>
 * @version : [v1.0]
 * @description : TODO json工具类
 * @createTime : [2024/4/6 13:58]
 * @updateUser : [TongJing]
 * @updateTime : [2024/4/6 13:58]
 * @updateRemark : [说明本次修改内容]
 */
public class JsonUtil {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    /**
     * @deprecated 将对象转换成json，如果失败直接toString
     * */
    public static String toJsonString(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }
}
