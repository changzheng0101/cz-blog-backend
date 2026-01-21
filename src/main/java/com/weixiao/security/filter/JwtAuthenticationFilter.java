package com.weixiao.security.filter;

import com.weixiao.common.utils.JwtUtils;
import com.weixiao.security.userdetails.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. 检查请求头是否包含 Authorization 信息，且以 Bearer 开头
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 提取 Token
        jwt = authHeader.substring(7);

        // 3. 从 Token 中提取用户名
        try {
            username = jwtUtils.extractUsername(jwt);
        } catch (Exception e) {
            log.error("JWT token extraction failed: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 如果用户名存在且 SecurityContext 中未认证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. 加载用户信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. 验证 Token 有效性
            if (jwtUtils.validateToken(jwt, userDetails)) {
                // 7. 构建认证 Token 并设置到 SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}