package com.linmoblog.server.Entity.vo;

import com.linmoblog.server.Entity.Category;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 前端文章分类 VO
 * @author wangkaiqi
 * @since 2024-04-01
 */
@Data
public class CategoryVO {
    private long categoryKey;
    private String categoryTitle;
    private String pathName;
    private String introduce;
    private String icon;
    private String color;
    private long noteCount;

    public CategoryVO(Category category) {
        this.categoryKey = category.getCategoryKey();
        this.categoryTitle = category.getCategoryTitle();
        this.pathName = category.getPathName();
        this.introduce = category.getIntroduce();
        this.icon = category.getIcon();
        this.color = category.getColor();
    }
    public CategoryVO(Category category,long noteCount) {
        this(category);
        this.noteCount = noteCount;
    }
}
