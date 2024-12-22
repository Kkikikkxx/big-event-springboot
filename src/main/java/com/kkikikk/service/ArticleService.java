package com.kkikikk.service;

import com.kkikikk.pojo.Article;
import com.kkikikk.pojo.PageBean;

public interface ArticleService {

    // 添加文章
    void addArticle(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //根据id查询文章
    Article findById(Integer id);

    //更新文章
    void updateArticle(Article article);

    //删除文章
    void deleteArticle(Integer id);

}
