package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.CategoryDao;
import com.linmoblog.server.Entity.Category;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Entity.vo.CategoryVO;
import com.linmoblog.server.Entity.vo.Pair;
import com.linmoblog.server.Mapper.CategoryMapper;
import com.linmoblog.server.enums.NoteStatusEnum;
import com.linmoblog.server.enums.ResultCode;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private NoteService noteService;

    public  List<CategoryVO> getCategoryList() {
        List<Category> categories = categoryMapper.getCategories();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<CategoryVO> list = new ArrayList<>();
        //2. 获取 category 中的 categoryKey
        List<Integer> categoryKeyList = categories.stream().map(Category::getCategoryKey).collect(Collectors.toList());
        //3. 根据 categoryKeyList 查询文章数量
       List<Pair<Integer,Integer>> pairList =  noteService.getNoteCountByCategoryKey(categoryKeyList);
        //4. 将文章数量添加到 category 中
        for (Category category : categories) {
            CategoryVO categoryVO = new CategoryVO(category);
            for (Pair<Integer, Integer> pair : pairList) {
                if (categoryVO.getCategoryKey().equals(pair.getPairKey())) {
                    categoryVO.setNoteCount(((Number)pair.getPairValue()).intValue());
                    break;
                }
            }
            list.add(categoryVO);
        }
        return list;
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
