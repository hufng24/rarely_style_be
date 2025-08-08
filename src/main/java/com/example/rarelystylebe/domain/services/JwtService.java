package com.example.rarelystylebe.domain.services;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.Map;

public interface JwtService {

    String generateAccessToken(String username, Map<String, Object> extraClaims);

    String generateRefreshToken(String username);

    Claims extractAllClaims(String token);

    String extractUsername(String token);

    Date extractExpiration(String token);

    boolean isTokenValid(String token);
}
