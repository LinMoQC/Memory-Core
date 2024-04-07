package com.linmoblog.server.Utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenUtil {


    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expire}")
    private long expire; //单位毫秒

    //生成令牌
    public String CreateToken(String username) {
        return dealJwt(username, expire);
    }

    /**
     * 生成 JWT
     *
     * @param body       内容
     * @param expireTime 过期时间
     * @return
     */
    private String dealJwt(String body, Long expireTime) {
        //1. 对密钥进行base64编码
        byte[] base64 = Base64.getEncoder().encode(key.getBytes());
        //2. 对 base64 生成一个密钥的对象
        SecretKey secretKey = Keys.hmacShaKeyFor(base64);
        if (expireTime == null) {
            //不过期
            return generatorJWTDefault(body, secretKey);
        }
        return generatorJWTDefaultExpire(body, secretKey, expireTime);
    }


    private String generatorJWTDefault(String body, SecretKey secretKey) {
        String jwtTokenString = Jwts.builder().setSubject(body)//设置用户自定义数据
                .signWith(secretKey) //设置加密算法和密钥
                .compact() //压缩并生成 jwt
                ;
        return jwtTokenString;
    }

    private String generatorJWTDefaultExpire(String body, SecretKey secretKey, long expireTime) {
        String jwtTokenString = Jwts.builder().setSubject(body)//设置用户自定义数据
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) //设置过期时间
                .signWith(secretKey) //设置加密算法和密钥
                .compact() //压缩并生成 jwt
                ;
        return jwtTokenString;
    }

    //验证令牌
    public Boolean validateToken(String token) {
        try {
            //模拟前端传过来的 jwt
            //1. 对密钥进行base64编码
            byte[] base64 = Base64.getEncoder().encode(key.getBytes());
            //2. 对 base64 生成一个密钥的对象
            SecretKey secretKey = Keys.hmacShaKeyFor(base64);
            //3. 解析 jwt
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build(); //构造解析器

            //解析 jwt，解析的过程其实就是校验的过程
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            return true;
        } catch (WeakKeyException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                 SignatureException | IllegalArgumentException e) {
            return false;
        }
    }
}
