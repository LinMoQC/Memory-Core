package com.linmoblog.server.search.annotation;

import java.lang.annotation.*;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsIndex {

    /**
     * 索引
     */
    String uid() default "";

    /**
     * 主键
     */
    String primaryKey() default "";

    /**
     * 分类最大数量
     */
    int maxValuesPerFacet() default 100;

    /**
     *  单次查询最大数量
     */
    int maxTotalHits() default 1000;
}
