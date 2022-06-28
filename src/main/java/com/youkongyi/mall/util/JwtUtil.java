package com.youkongyi.mall.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
  * @description： JwtToken生成的工具类
  *     com.youkongyi.mall.util.JwtUtil
  * @author： Aimer
  * @crateDate： 2022/06/28 09:29
  */
@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secret}")
    private String secret;

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
      * @description： 生成token
      *     com.youkongyi.mall.util.JwtUtil.generateToken
      * @param： user (java.lang.String) 用户信息
      * @param： secret (java.lang.String) 密钥
      * @param： expiration (java.lang.Integer) 过期时间
      * @return： java.lang.String
      * @author： Aimer
      * @crateDate： 2022/06/28 10:59
      */
    public String generateToken(String user){
        return Jwts.builder()
                .setIssuer("youkongyi") // 签发者
                .setSubject("mall") // 面向用户
                .setAudience(user)// 接收者
                .setExpiration(DateUtil.offsetMillisecond(DateUtil.date(), expiration)) // 设置过期时间
//                .setNotBefore(DateUtil.date()) // 设置在此时间之后生效
                .setIssuedAt(DateUtil.date())// 设置注册时间
                .setId(UUID.randomUUID().toString())// 唯一标识
                .signWith(this.generateKeys())
                .compact();
    }

    /**
      * @description： 生成key
      *     com.youkongyi.mall.util.JwtUtil.generateKeys
      * @return： java.security.Key
      * @author： Aimer
      * @crateDate： 2022/06/28 11:11
      */
    private Key generateKeys(){
        String str = redisUtil.get(secret, String.class);
        if(StringUtils.isBlank(str)){
            // 生成key
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.ES512);
            str = Encoders.BASE64.encode(key.getEncoded());
            redisUtil.set(secret, str, TimeUnit.MILLISECONDS.toSeconds(expiration));
        }
        return Keys.hmacShaKeyFor(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
      * @description： 解密token
      *     com.youkongyi.mall.util.JwtUtil.getClaimsFromToken
      * @param： token (java.lang.String)
      * @return： io.jsonwebtoken.Claims
      * @author： Aimer
      * @crateDate： 2022/06/28 10:04
      */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(this.generateKeys())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

}
