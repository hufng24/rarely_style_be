package com.example.rarelystylebe.app.dtos.response;

import com.example.rarelystylebe.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

  private String accessToken;

  private String refreshToken;

  private UserResponse userInfo;
}
