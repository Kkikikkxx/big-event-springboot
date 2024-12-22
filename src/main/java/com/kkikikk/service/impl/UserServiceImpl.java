package com.kkikikk.service.impl;

import com.kkikikk.mapper.UserMapper;
import com.kkikikk.pojo.User;
import com.kkikikk.service.UserService;
import com.kkikikk.utils.Md5Util;
import com.kkikikk.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 查询到的用户信息，如果不存在返回null
     */
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    public void register(String username, String password) {
        // 密码加密处理，确保用户密码安全
        String md5String = Md5Util.getMD5String(password);
        // 执行注册操作
        userMapper.addUser(username, md5String);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserInfo(user);
    }

    /**
     * 更新用户头像
     *
     * @param avatarUrl 头像地址
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    /**
     * 更新用户密码
     *
     * @param newPwd 新密码
     */
    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}
