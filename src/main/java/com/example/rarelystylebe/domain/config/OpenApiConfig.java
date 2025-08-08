package com.example.rarelystylebe.domain.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Customer API", version = "1.0", description = "Customer Service API"),
    servers = {
      @Server(url = "http://localhost:8080"),
    },
    security = @SecurityRequirement(name = "bearerAuth"))
@SecuritySchemes({
  @SecurityScheme(
      name = "bearerAuth",
      type = SecuritySchemeType.HTTP,
      scheme = "bearer",
      bearerFormat = "JWT",
      description = "Enter JWT Bearer Token here")
})
public class OpenApiConfig {}