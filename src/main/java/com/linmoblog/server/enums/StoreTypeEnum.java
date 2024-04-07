package com.linmoblog.server.enums;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wkq97@qq.com
 */
public enum StoreTypeEnum {
    LOCAL("local"),

    ALI("ali");

    private String code;

    StoreTypeEnum(String code) {
        this.code = code;
    }



    public String getCode() {
        return code;
    }

    /**
     * 根据 code 获取 enum
     */
    public static StoreTypeEnum getEnumByCode(String code){
        Optional<StoreTypeEnum> first = Arrays.stream(values()).filter(storeTypeEnum -> Objects.equals(code, storeTypeEnum.code)).findFirst();
        return first.orElse(null);
    }

}
