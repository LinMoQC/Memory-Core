package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.CategoryDao;
import com.linmoblog.server.Entity.Category;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Entity.vo.CategoryVO;
import com.linmoblog.server.Mapper.CategoryMapper;
import com.linmoblog.server.enums.ResultCode;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Resource
    private CategoryMapper categoryMapper;

    public  List<Category> getCategoryList() {
        List<Category> categories = categoryMapper.getCategories();
        //2. 获取 category 中的 categoryKey
        List<Long> categoryKeyList = categories.stream().map(Category::getCategoryKey).collect(Collectors.toList());
        //3. 根据 categoryKeyList 查询文章数量
        return categories;
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
