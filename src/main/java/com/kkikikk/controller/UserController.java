package com.kkikikk.controller;

import com.kkikikk.pojo.Result;
import com.kkikikk.pojo.User;
import com.kkikikk.service.UserService;
import com.kkikikk.utils.JwtUtil;
import com.kkikikk.utils.Md5Util;
import com.kkikikk.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户控制器类，处理用户相关的HTTP请求
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    /**
     * 用户服务，用于调用用户相关的业务逻辑
     */
    @Autowired
    UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册接口
     *
     * @param username 用户名，符合正则表达式"^\\S{5,16}$"，即长度为5到16个字符且不包含空格
     * @param password 密码，符合正则表达式"^\\S{5,16}$"，即长度为5到16个字符且不包含空格
     * @return 注册结果，成功返回Result.success()，用户名已存在返回Result.error("用户名已被占用")
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户信息
        User u = userService.findByUserName(username);
        if (u == null) {
            // 如果用户名未被占用，则注册新用户
            userService.register(username, password);
            return Result.success();
        } else {
            // 如果用户名已被占用，返回错误信息
            return Result.error("用户名已被占用");
        }
    }

    /**
     * 用户登录接口
     * 通过POST请求接收用户登录信息
     *
     * @param username 用户名，符合长度5到16个字符的规则
     * @param password 密码，符合长度5到16个字符的规则
     * @return 返回登录结果，包括错误信息或成功时的令牌
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        // 判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }
        // 判断密码是否正确,loginUser 对象中的password是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 12, TimeUnit.HOURS);

            return Result.success(token);
        }
        // 密码错误
        return Result.error("密码错误");
    }


    /**
     * 获取当前用户信息
     * <p>
     * 通过用户名查询并返回当前用户的信息
     * 使用了ThreadLocal来存储会话信息，以避免在请求处理过程中丢失用户身份信息
     *
     * @return 包含用户信息的Result对象，表示操作成功并携带了用户数据
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        // 获取存储在ThreadLocal中的会话信息
        Map<String, Object> map = ThreadLocalUtil.get();
        // 从会话信息中提取用户名
        String username = (String) map.get("username");
        // 根据用户名查询用户信息
        User user = userService.findByUserName(username);
        // 返回查询到的用户信息，使用Result对象封装
        return Result.success(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 更新操作的结果，成功返回Result.success()
     */
    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated User user) {
        // 调用userService的updateUserInfo方法来更新用户信息
        userService.updateUserInfo(user);

        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 更新用户头像
     * 通过PATCH请求接收用户上传的新头像URL，并更新至用户信息中
     * 此接口方法不返回任何内容，仅确认头像更新操作的执行结果
     *
     * @param avatarUrl 新的头像URL地址，通过@RequestParam注解接收，使用@URL注解确保输入是有效的URL格式
     * @return 返回一个Result对象，表示操作结果，此处始终返回成功结果，表示头像更新成功
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    /**
     * 使用Patch请求处理密码更新操作
     *
     * @param params 包含旧密码、新密码和确认密码的Map对象
     * @return 操作结果，包括成功或错误信息
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // 检查密码是否为空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少参数");
        }

        // 获取数据库中的密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);

        // 比较输入的旧密码和数据库中的密码是否匹配
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        // 确认新密码和重复密码是否一致
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次密码不一致");
        }

        // 更新密码
        userService.updatePwd(newPwd);

        //密码更新成功后,需要删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }


}

