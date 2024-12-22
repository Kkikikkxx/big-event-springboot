package com.kkikikk.mapper;

import com.kkikikk.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增文章分类
    @Insert("insert into category( category_name, category_alias, create_user,create_time,update_time) " +
            "values( #{categoryName}, #{categoryAlias}, #{createUser},#{createTime},#{updateTime})")
    void addCategory(Category category);

    //查询文章分类
    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    //根据id查询分类
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    //修改分类
    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = #{updateTime} where id = #{id}")
    void updateCategory(Category category);

    //删除分类
    @Delete("delete from category where id = #{id}")
    void deleteCategory(Integer id);
}
