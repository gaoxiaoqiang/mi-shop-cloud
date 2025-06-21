package com.tst.mall.util;

import com.tst.mall.model.dto.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JwtTokenUtil {

    // 安全密钥（生产环境应从配置中获取）
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Access Token有效期（建议15-30分钟）
    private static final long ACCESS_TOKEN_EXPIRATION = TimeUnit.MINUTES.toMillis(30);

    // Refresh Token有效期（建议7天）
    private static final long REFRESH_TOKEN_EXPIRATION = TimeUnit.DAYS.toMillis(7);

    /**
     * 生成Access Token
     * @return JWT Token字符串
     */
    public static String generateAccessToken(UserPrincipal userDetails) {
        Map<String, Object> claims = buildCommonClaims(userDetails);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 生成Refresh Token
     */
    public static String generateRefreshToken(UserPrincipal userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getId()); // 只包含必要的最小信息
        claims.put("token_type", "refresh");

      return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 构建公共的JWT声明
     */
    private static Map<String, Object> buildCommonClaims(UserPrincipal userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // 标准声明
        claims.put("sub", userDetails.getId()); // 主题标识
        claims.put("iss", "mi-shop"); // 签发者
        claims.put("aud", "client-mi");       // 接收方
        claims.put("token_type", "token");

        // 自定义声明（从OAuth2用户信息中提取）
        claims.put("username", userDetails.getUsername());
        claims.put("userId", userDetails.getId());
        claims.put("provider", "github"); // 标识认证提供者

        return claims;
    }

    /**
     * 验证Token有效性
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取用户标识
     */
    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 从Token中获取声明信息
     */
    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 从令牌获取Authentication对象
    public  static  Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        String email = claims.get("userEmail", String.class);

        UserPrincipal userPrincipal = new UserPrincipal(
                userId,
                username,
                email,
                null, // 密码不需要
                null,
                null // 属性不需要
        );

        return new UsernamePasswordAuthenticationToken(
                userPrincipal,
                null, // 凭证
                null
        );
    }

}
