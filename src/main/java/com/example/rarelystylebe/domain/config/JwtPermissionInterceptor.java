package com.example.rarelystylebe.domain.config;

import com.example.rarelystylebe.domain.annotation.RequirePermissions;
import com.example.rarelystylebe.domain.services.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtPermissionInterceptor implements HandlerInterceptor {

    private final RedisService redisService;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        RequirePermissions annotation = method.getMethodAnnotation(RequirePermissions.class);

        if (annotation == null) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Chưa có token");
            return false;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            // ✅ Check token blacklist trong Redis
            String redisKey = "blacklist_token:" + token;
            if (redisService.getData(redisKey) != null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token đã bị thu hồi");
                return false;
            }

            // ✅ Check hết hạn
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token đã hết hạn");
                return false;
            }

            // ✅ Check permission
            List<String> permissions = claims.get("permissions", List.class);
            for (String requiredPermission : annotation.value()) {
                if (permissions == null || !permissions.contains(requiredPermission)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền: " + requiredPermission);
                    return false;
                }
            }

            return true;

        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or malformed token");
            return false;
        }
    }
}

