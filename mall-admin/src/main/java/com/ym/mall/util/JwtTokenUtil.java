package com.ym.mall.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwtToken生成的工具类
 * claim:声称，宣称
 *
 * @author matao
 * @create 2019-08-01 18:19
 */
@Component
@Slf4j
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    //密钥
    @Value("${jwt.secret}")
    private String secret;
    //有效时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负荷生成JWT的token
     * @param claims
     * @return
     */
    public String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())   //设置生成token的过期时间
                .signWith(SignatureAlgorithm.HS512,secret)   //SignatureAlgorithm:签名算法
                .compact();   //compact：压缩，使紧密
    }

    /**
     * 生成token的过期时间
     * @return
     */
    public Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取JWT中的负载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJwt(token)
                    .getBody();
        }catch (Exception e){
            log.info("JWT格式验证失败：{}",token);
        }
        return claims;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username;
        try{
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
            log.info("从token中获取用户名失败：{}",token);
        }
        return username;
    }

    /**
     * 验证token是否有效
     * @param token   客户端传入的token
     * @param userDetails    从数据库查询出来的用户信息
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date()); //当expiredDate小于new Date()时，返回TRUE，当大于等于时，返回false
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    public Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以刷新
     * @param token
     * @return
     */
    public boolean canReflesh(String token){
        return !isTokenExpired(token);   //判断token是否已经失效，若失效，则可以刷新吗，反之
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refleshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }
}