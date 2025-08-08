package com.example.rarelystylebe.domain.services;


import com.example.rarelystylebe.app.dtos.filter.UserParam;
import com.example.rarelystylebe.app.dtos.request.AssignUserRoleRequest;
import com.example.rarelystylebe.app.dtos.request.LoginRequest;
import com.example.rarelystylebe.app.dtos.request.UserRequest;
import com.example.rarelystylebe.app.dtos.response.AssignUserRoleResponse;
import com.example.rarelystylebe.app.dtos.response.LoginResponse;
import com.example.rarelystylebe.app.dtos.response.UserResponse;
import com.example.rarelystylebe.domain.entities.User;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id,UserRequest userRequest) throws JsonMappingException;

    void deleteUser(Long id);

    User findById(Long id);

    LoginResponse login(LoginRequest request);

    AssignUserRoleResponse assignUserRole(AssignUserRoleRequest assignUserRoleRequest);

    void changePassword(String username, String accessToken, String oldPassword, String newPassword);

    Page<UserResponse> filter (UserParam userParam, Pageable pageable);

}
