package com.kkikikk.mapper;

import com.kkikikk.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * 该方法使用了MyBatis的@Select注解，直接传入SQL语句进行查询
     *
     * @param username 用户名
     * @return User对象，包含用户的所有信息
     */
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    /**
     * 添加用户
     * 该方法使用了MyBatis的@Insert注解，直接传入SQL语句进行插入操作
     *
     * @param username 用户名
     * @param password 密码
     *                 注意：SQL语句中使用了now()函数为create_time和update_time字段自动设置当前时间
     */
    @Insert("insert into user(username, password, create_time,update_time)" +
            " values(#{username}, #{password}, now(),now())")
    void addUser(String username, String password);


    /**
     * 更新用户信息
     * 该方法使用了MyBatis的@Update注解，直接传入SQL语句进行更新操作
     *
     * @param user 包含用户信息的User对象
     */
    @Update("update user set nickname = #{nickname}, email = #{email}, user_pic = #{userPic}, update_time = #{updateTime} where id = #{id}")
    void updateUserInfo(User user);

    /**
     * 更新用户头像
     * 该方法使用了MyBatis的@Update注解，直接传入SQL语句进行更新操作
     *
     * @param avatarUrl 用户头像URL
     * @param id        用户ID
     */
    @Update("update user set user_pic = #{avatarUrl},update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    /**
     * 更新用户密码
     * 该方法使用了MyBatis的@Update注解，直接传入SQL语句进行更新操作
     *
     * @param md5String 用户密码的MD5加密后的字符串
     * @param id        用户ID
     */
    @Update("update user set password = #{md5String},update_time = now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}

