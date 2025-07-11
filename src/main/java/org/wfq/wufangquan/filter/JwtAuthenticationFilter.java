package org.wfq.wufangquan.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.service.JwtService;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    final private JwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;
    boolean refreshVerification = true;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (refreshVerification) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String refreshToken = null;
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
            if (refreshToken == null) {
                filterChain.doFilter(request, response);
                return;
            }


            JwtPayload refreshPayload;
            try {
                // 从 refresh_token 中解析
                refreshPayload = jwtService.extractPayload(refreshToken);
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return ;
            }

            // 从 Redis 中获取用户对应的 refresh_token
            String redisToken = redisTemplate.opsForValue().get("refresh:" + refreshPayload.getUser_id());
            if (redisToken == null) {
                filterChain.doFilter(request, response);
                return ;
            }

            // 比对 Redis 中存储的 refresh_token 和前端传来的 refresh_token
            if (!redisToken.equals(refreshToken)) {
                filterChain.doFilter(request, response);
                return ;
            }
        }



        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // 放行，没有 token
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (jwtService.isTokenValid(token)) {
                JwtPayload payload = jwtService.extractPayload(token);  // 解析 token 得到 payload

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(payload, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
