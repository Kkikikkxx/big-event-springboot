package com.kkikikk.service.impl;

import com.kkikikk.mapper.CategoryMapper;
import com.kkikikk.pojo.Category;
import com.kkikikk.service.CategoryService;
import com.kkikikk.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加新的分类
     *
     * @param category 要添加的分类对象
     */
    @Override
    public void addCategory(Category category) {
        // 设置分类的创建时间和更新时间为当前时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 从线程局部变量中获取当前操作用户的ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 设置分类的创建用户ID
        category.setCreateUser(userId);

        // 调用Mapper将分类信息添加到数据库
        categoryMapper.addCategory(category);
    }

    /**
     * 获取当前用户的分类列表
     * <p>
     * 此方法通过查询数据库，根据用户ID获取特定用户的分类列表
     * 它使用了ThreadLocal来存储和检索用户ID，从而避免了在请求范围内传递ID的需要
     *
     * @return 返回一个包含特定用户所有分类的列表
     */
    @Override
    public List<Category> list() {
        // 从ThreadLocal中获取当前会话的用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 调用持久层方法，传入用户ID，获取该用户的分类列表
        return categoryMapper.list(userId);
    }

    /**
     * 根据ID查找分类信息
     *
     * 该方法通过调用categoryMapper的findById方法，实现根据分类ID查询对应分类信息的功能
     *
     * @param id 分类的唯一标识符
     * @return 查询到的分类信息，如果找不到则返回null
     */
    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    /**
     * 更新类别信息
     *
     * 此方法主要用于更新类别的信息它首先更新类别对象的更新时间，
     * 然后调用mapper层的方法来实现数据库中类别信息的更新
     *
     * @param category 需要更新的类别对象，包含要更新的所有信息
     */
    @Override
    public void updateCategory(Category category) {
        // 设置类别的更新时间为当前时间，确保时间的准确性
        category.setUpdateTime(LocalDateTime.now());

        // 调用mapper层的方法，将更新后的类别信息同步到数据库中
        categoryMapper.updateCategory(category);
    }

    /**
     * 删除类别
     *
     * 此方法主要用于删除类别，它首先通过调用mapper层的方法，将指定ID的类别从数据库中删除
     *
     * @param id 要删除的类别的ID
     */
    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }

}
