package kkikikk;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {
        //初始化载荷信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");

        //生成JWT代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) //添加过期时间
                .sign(Algorithm.HMAC256("kkikikk")); //指定算法,配置密钥
        System.out.println(token);
    }

    /**
     * 测试解析JWT令牌
     * 此测试方法用于验证JWT令牌是否能被正确解析，并提取出其中的用户信息
     */
    @Test
    public void testParse() {
        //定义字符串,模拟用户传来的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJleHAiOjE3MjMwMzkwNjIsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19" +
                ".r9cVze5Oa8aRYLtc4dy7QmTNSDxTZnusN8LdsZPriG4";

        //创建JWT验证器，使用HS256算法和密钥"kkikikk"
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("kkikikk")).build();

        //使用验证器解析token，得到JWT对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        //从JWT对象中获取所有声明（claims）
        Map<String, Claim> claims = decodedJWT.getClaims();
        //打印出claims中的"user"信息
        System.out.println(claims.get("user"));
    }


}
