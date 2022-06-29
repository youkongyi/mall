package com.youkongyi.mall.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

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
    public String generateToken(String user) {
        return Jwts.builder()
                .setIssuer("youkongyi") // 签发者
                .setSubject("mall") // 面向用户
                .setAudience(user)// 接收者
                .setIssuedAt(DateUtil.date())// 设置注册时间
                .setNotBefore(DateUtil.date()) // 设置在此时间之后生效
                .setExpiration(DateUtil.offsetMillisecond(DateUtil.date(), expiration)) // 设置过期时间
                .setId(UUID.randomUUID().toString())// 唯一标识
                .signWith(this.generateKeys(), SignatureAlgorithm.HS512)
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
        Digester sha = new Digester(DigestAlgorithm.SHA512);
        String secretString = Encoders.BASE64.encode(sha.digest(secret));
        return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
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

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getClaimsFromToken(token).getAudience();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
