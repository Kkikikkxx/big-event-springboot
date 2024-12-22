package com.kkikikk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "itheima";

    /**
     * 接收业务数据,生成token并返回
     * 该方法使用JWT库创建一个带有特定声明和过期时间的令牌，并使用预定义的密钥进行签名
     *
     * @param claims 一个包含业务数据的Map对象，将作为令牌的一部分进行签名
     * @return 生成的JWT令牌字符串
     */
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }


    /**
     * 接收并验证token，然后返回业务数据
     * 该方法使用JWT库对token进行验证，并从验证通过的token中提取出业务数据（claims）
     *
     * @param token 待验证的JWT token字符串
     * @return 包含业务数据的Map对象，如果token验证失败，则返回空Map
     */
    public static Map<String, Object> parseToken(String token) {
        // 使用HMAC256算法和预设的密钥创建JWT验证器
        // 注意：这里没有显示异常处理，实际使用中应当处理可能的异常，如算法不匹配或签名验证失败
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }


}
