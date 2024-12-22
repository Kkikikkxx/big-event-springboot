package com.kkikikk.mapper;

import com.kkikikk.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);

    //查询文章列表
    List<Article> list(Integer userId, Integer categoryId, String state);

    //根据用户ID和文章ID查询文章详情
    @Select("select * from article where id = #{id} and create_user = #{userId}")
    Article findById(Integer userId, Integer id);

    //根据文章ID修改文章
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId} where id = #{id}")
    void updateArticle(Article article);

    //根据文章ID删除文章
    @Delete("delete from article where id = #{id} and create_user = #{userId}")
    void deleteArticle(Integer id, Integer userId);
}
