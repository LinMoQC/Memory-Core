package com.linmoblog.server.search.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsField {

    /**
     * 是否开启过滤
     */
    boolean openFilter() default false;

    /**
     * 是否不展示
     */
    boolean noDisplayed() default false;

    /**
     * 是否开启排序
     */
    boolean openSort() default false;


    /**
     *  处理的字段名
     */
    String key();

    /**
     * 字段说明
     */
    String description() default StringUtils.EMPTY;

}