package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.request.AssignRolePermissionRequest;
import com.example.rarelystylebe.app.dtos.request.RoleRequest;
import com.example.rarelystylebe.app.dtos.response.AssignRolePermissionResponse;
import com.example.rarelystylebe.app.dtos.response.RoleResponse;
import com.example.rarelystylebe.domain.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller để quản lý các thao tác liên quan đến Role (vai trò người dùng)
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Api vai trò", description = "Quản lý vai trò và phân quyền")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleResponse createRole(@Valid @RequestBody RoleRequest roleRequest) {
        return roleService.createRole(roleRequest);
    }
    

    @PostMapping("/assignt")
    public AssignRolePermissionResponse assignRole(@Valid @RequestBody AssignRolePermissionRequest assignRolePermissionRequest) {
        return roleService.assignRolePermission(assignRolePermissionRequest);
    }


}
