package com.example.rarelystylebe.domain.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtPermissionInterceptor jwtPermissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtPermissionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "api/v1/auth/login",
                        "api/v1/auth/change_password",
                        "api/v1/auth/register",
                        "api/v1/auth/check"
                );
        ;
    }

}

