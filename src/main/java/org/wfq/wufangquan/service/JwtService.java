package org.wfq.wufangquan.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.entity.JwtPayload;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.wfq.wufangquan.util.ReflectUtils.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 30; // 30分钟
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7天

    // 获取加密密钥
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 生成 Access Token（有效期 30分钟）
    public String generateAccessToken(JwtPayload payload) {
        Map<String, Object> claims = objectToMap(payload);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 生成 Refresh Token（有效期 7天）
    public String generateRefreshToken(JwtPayload payload) {
        Map<String, Object> claims = objectToMap(payload);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 Token，获取 user_id
    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user_id", String.class);
    }

    // 验证 Token 是否有效
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // 如果解析成功，说明有效
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 如果验证失败（如过期、无效等），返回 false
            return false;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtPayload extractPayload(String token) {
        Claims claims = parseToken(token);
        JwtPayload payload = new JwtPayload();
        copyCommonFields(payload, claims);
        return payload;
    }

    public void setTokenForUserId(RedisTemplate<String, String> redisTemplate, String userId, String refreshToken, String accessToken, HttpServletResponse response) {
        // 保存 refresh_token 到 Redis，key 可以加 user_id 防止重复登录
        redisTemplate.opsForValue().set("refresh:" + userId, refreshToken, 7, TimeUnit.DAYS);
        // 返回 access_token，或设置到响应头
        response.addHeader("Authorization", "Bearer " + accessToken);
        // 返回 refresh_token 到前端（可以用 cookie 或响应体）
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    // 根据 user_id 生成 Access Token
    public String generateAccessTokenByUserId(JwtPayload payload) {
        return generateAccessToken(payload);
    }

    // 根据 user_id 生成 Refresh Token
    public String generateRefreshTokenByUserId(JwtPayload payload) {
        return generateRefreshToken(payload);
    }
}