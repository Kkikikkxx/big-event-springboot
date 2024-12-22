package com.kkikikk.service;

import com.kkikikk.pojo.User;

public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUserName(String username);

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    void register(String username, String password);


    /**
     * 更新用户信息
     *
     * 此方法接收一个User对象作为参数，用于更新用户的个人信息
     * 它不返回任何值
     *
     * @param user 包含更新后用户信息的User对象
     */
    void updateUserInfo(User user);

    /**
     * 更新用户头像
     *
     * @param avatarUrl 用户头像的URL地址
     */
    void updateAvatar(String avatarUrl);

    /**
     * 更新用户密码
     *
     * @param newPwd 新密码
     */
    void updatePwd(String newPwd);
}
