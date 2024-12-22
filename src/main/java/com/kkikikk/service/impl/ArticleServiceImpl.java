package com.kkikikk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kkikikk.mapper.ArticleMapper;
import com.kkikikk.pojo.Article;
import com.kkikikk.pojo.PageBean;
import com.kkikikk.service.ArticleService;
import com.kkikikk.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 添加新文章
     *
     * @param article 要添加的文章对象
     *                <p>
     *                该方法首先为文章对象设置创建时间和更新时间为当前时间，
     *                并从线程局部变量中获取当前操作用户的ID，设置为文章的创建者ID，
     *                然后调用文章映射器将文章对象添加到数据存储中
     */
    @Override
    public void addArticle(Article article) {
        // 设置文章的创建时间和更新时间为当前时间
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        // 从线程局部变量中获取当前操作用户的ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 设置文章的创建者ID
        article.setCreateUser(userId);

        // 调用文章映射器将文章对象添加到数据存储中
        articleMapper.addArticle(article);
    }

    /**
     * 根据指定的页面编号、页面大小、类别ID和状态查询文章列表
     *
     * @param pageNum    当前页码
     * @param pageSize   每页显示的数量
     * @param categoryId 文章类别ID，用于筛选特定类别的文章
     * @param state      文章的状态，用于筛选特定状态的文章
     * @return 返回包含文章列表的PageBean对象
     */
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        //创建pageBean对象
        PageBean<Article> pageBean = new PageBean<>();
        //开启分页查询PageHelper
        PageHelper.startPage(pageNum, pageSize);

        //从ThreadLocalUtil中获取当前用户ID，用于查询特定用户的文章
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //调用mapper查询对应用户ID、类别ID和状态的文章列表
        List<Article> articles = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Article> page = (Page<Article>) articles;

        //把数据填充到PageBean对象中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;

    }

    /**
     * 根据文章ID和用户ID查询文章详情
     *
     * @param id 文章ID
     * @return 返回文章详情对象
     */
    @Override
    public Article findById(Integer id) {
        // 从线程局部变量中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        return articleMapper.findById(userId, id);
    }

    /**
     * 更新文章信息
     * 该方法在文章更新时被调用，主要负责记录文章的更新时间并调用数据库操作方法更新文章
     *
     * @param article 需要更新的文章对象，包含文章的所有信息
     */
    @Override
    public void updateArticle(Article article) {
        // 设置文章的更新时间为当前时间
        article.setUpdateTime(LocalDateTime.now());

        // 调用数据库操作方法，更新文章信息
        articleMapper.updateArticle(article);
    }


    /**
     * 删除指定文章
     *
     * <p>
     * 本方法通过调用数据库访问层的方法，根据用户ID和文章ID删除指定的文章
     * 它从线程局部变量中获取用户ID，并将其作为参数传递给删除操作
     * 这里的设计确保了在多线程环境下，每个线程操作的数据都是隔离的
     * </p>
     *
     * @param id 文章的ID，用于标识要删除的文章
     */
    @Override
    public void deleteArticle(Integer id) {
        // 从线程局部变量中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        // 提取用户ID
        Integer userId = (Integer) map.get("id");

        // 调用数据库访问层的方法，执行删除操作
        articleMapper.deleteArticle(id, userId);
    }

}
