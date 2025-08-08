package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.request.AssignUserRoleRequest;
import com.example.rarelystylebe.app.dtos.request.UserRequest;
import com.example.rarelystylebe.app.dtos.response.AssignUserRoleResponse;
import com.example.rarelystylebe.app.dtos.response.UserResponse;
import com.example.rarelystylebe.domain.services.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }

    @PostMapping("/assign-user")
    public AssignUserRoleResponse assignUser(@Valid @RequestBody AssignUserRoleRequest assignUserRoleRequest){
        return userService.assignUserRole(assignUserRoleRequest);
    }

    @PutMapping
    public UserResponse updateUser(@Valid @RequestBody Long id, UserRequest userRequest) throws JsonMappingException {
        return userService.updateUser(id,userRequest);
    }
}
