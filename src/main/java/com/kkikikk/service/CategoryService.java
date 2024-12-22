package com.kkikikk.service;

import com.kkikikk.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    /**
     * 添加分类
     *
     * @param category
     */
    void addCategory(Category category);

    /**
     * 查询所有分类
     *
     * @return
     */
    List<Category> list();

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 修改分类
     *
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id
     */
    void deleteCategory(Integer id);
}
