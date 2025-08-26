package com.example.rarelystylebe.domain.services.Impl;


import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.UserParam;
import com.example.rarelystylebe.app.dtos.request.AssignUserRoleRequest;
import com.example.rarelystylebe.app.dtos.request.LoginRequest;
import com.example.rarelystylebe.app.dtos.request.RegisterRequest;
import com.example.rarelystylebe.app.dtos.request.UserRequest;
import com.example.rarelystylebe.app.dtos.response.AssignUserRoleResponse;
import com.example.rarelystylebe.app.dtos.response.LoginResponse;
import com.example.rarelystylebe.app.dtos.response.RoleResponse;
import com.example.rarelystylebe.app.dtos.response.UserResponse;
import com.example.rarelystylebe.domain.entities.Permission;
import com.example.rarelystylebe.domain.entities.Role;
import com.example.rarelystylebe.domain.entities.User;
import com.example.rarelystylebe.domain.enums.UserStatus;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.RoleRepository;
import com.example.rarelystylebe.domain.repositories.UserRepository;
import com.example.rarelystylebe.domain.services.JwtService;
import com.example.rarelystylebe.domain.services.RedisService;
import com.example.rarelystylebe.domain.services.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setGender(userRequest.getGender());
        user.setPhoneNumber(userRequest.getPhone_number());
        user.setAvatar(userRequest.getAvatar());
        user.setStatus(userRequest.getStatus());
        user.setIsAdmin(userRequest.getIsAdmin());

        Set<Role> roles = userRequest.getRoleIds().stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với id: " + id)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User saved = userRepository.save(user);

        // Map User → Map trước để convert bằng ObjectMapper
        Map<String, Object> userMap = objectMapper.convertValue(saved, Map.class);
        userMap.put("roles", roles.stream().map(Role::getName).collect(Collectors.toSet()));

        return objectMapper.convertValue(userMap, UserResponse.class);
    }


    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) throws JsonMappingException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + id));

        // Merge field có giá trị từ userRequest vào user
        objectMapper.updateValue(user, userRequest);

        // Encode password nếu có
        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        // Cập nhật roles nếu có
        if (userRequest.getRoleIds() != null && !userRequest.getRoleIds().isEmpty()) {
            Set<Role> roles = userRequest.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền với id: " + roleId)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User updated = userRepository.save(user);

        // Trả về UserResponse kèm tên role
        Map<String, Object> userMap = objectMapper.convertValue(updated, Map.class);
        userMap.put("roles", updated.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return objectMapper.convertValue(userMap, UserResponse.class);
    }


    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        user.setIsDeleted(true);
        userRepository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));
    }



    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND,"Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseException(HttpStatus.NOT_FOUND,"Invalid username or password");
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("isAdmin", user.getIsAdmin());

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        extraClaims.put("roles", roles);

        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getValue)
                .distinct()
                .toList();
        extraClaims.put("permissions", permissions);

        String accessToken = jwtService.generateAccessToken(user.getFullName(), extraClaims);
        String refreshToken = jwtService.generateRefreshToken(user.getFullName());

        String redisKey = "refresh_token:" + user.getFullName();
        redisService.saveDataWithTTL(redisKey, refreshToken, refreshTokenExpiration);

        UserResponse userInfo = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(roles)
                .gender(String.valueOf(user.getGender()))
                .isAdmin(user.getIsAdmin())
                .status(String.valueOf(user.getStatus()))
                .isDeleted(user.getIsDeleted())
                .build();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(userInfo)
                .build();
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    @Override
    public AssignUserRoleResponse assignUserRole(AssignUserRoleRequest assignUserRoleRequest) {
        User user = userRepository.findByEmail(assignUserRoleRequest.getEmail()).orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND,"KHONG TIM THAY EMAIL"));
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(assignUserRoleRequest.getRoleIds()));
        user.setRoles(roles);
        User saved = userRepository.save(user);
        Set<RoleResponse> roleResponses = saved.getRoles().stream()
                .map(r -> new RoleResponse(r.getId(), r.getName()))
                .collect(Collectors.toSet());

        return new AssignUserRoleResponse(saved.getId(), saved.getFullName(), roleResponses);
    }


    @Override
    public void changePassword(String email, String accessToken, String oldPassword, String newPassword) {
        String blacklistKey = "blacklist_token:" + accessToken;
        if (redisService.getData(blacklistKey) != null) {
            throw new RuntimeException("Token is revoked");
        }

        User user = findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        long expirationMillis = jwtService.extractExpiration(accessToken).getTime();
        long now = System.currentTimeMillis();
        long ttl = expirationMillis - now;

        if (ttl > 0) {
            redisService.saveDataWithTTL(blacklistKey, "revoked", ttl);
        }
    }

    @Override
    public Page<UserResponse> filter(UserParam userParam, Pageable pageable) {
        return null;
    }



}
