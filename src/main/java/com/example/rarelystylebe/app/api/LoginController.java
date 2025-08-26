package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.request.ChangePasswordRequest;
import com.example.rarelystylebe.app.dtos.request.LoginRequest;
import com.example.rarelystylebe.app.dtos.response.LoginResponse;
import com.example.rarelystylebe.domain.annotation.RequirePermissions;
import com.example.rarelystylebe.domain.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {


    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
      return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid ChangePasswordRequest request) {

      String accessToken = authHeader.replaceFirst("(?i)^Bearer ", "");

      userService.changePassword(
              request.getUsername(), accessToken, request.getOldPassword(), request.getNewPassword()
      );

      return ResponseEntity.noContent().build();
    }
  }




//  @PostMapping("/register")
//  public ResponseEntity<String> registerEmail(@RequestParam String email) {
//    if (emailService.isEmailRegistered(email)) {
//      return ResponseEntity.badRequest().body("Email đã tồn tại");
//    }
//    emailService.registerEmail(email);
//    return ResponseEntity.ok("Đăng ký email thành công");
//  }
//
//  @GetMapping("/check")
//  public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
//    return ResponseEntity.ok(emailService.isEmailRegistered(email));
//  }

