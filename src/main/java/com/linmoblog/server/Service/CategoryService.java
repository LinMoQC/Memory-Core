package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.CategoryDao;
import com.linmoblog.server.Entity.Category;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Mapper.CategoryMapper;
import com.linmoblog.server.enums.ResultCode;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Resource
    private CategoryMapper categoryMapper;

    public Result<List<Category>> getCategoryList() {
        List<Category> categories = categoryDao.getCategories();
        return new Result<List<Category>>(ResultCode.SUCCESS, categories);
    }

    public Result<Null> addCategory(Category category) {
        categoryDao.addCategory(category);
        return new Result<Null>(ResultCode.SUCCESS);
    }

    public Result<Null> deleteCategory(List<Integer> categories) {
        categoryDao.deleteCategory(categories);
        return new Result<Null>(ResultCode.SUCCESS);
    }

    public void updateCategory(Integer id, Category category) {
        categoryMapper.updateCategory(id,category);
    }
}
