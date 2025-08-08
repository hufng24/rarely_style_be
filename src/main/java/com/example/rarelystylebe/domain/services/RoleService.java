package com.example.rarelystylebe.domain.services;


import com.example.rarelystylebe.app.dtos.request.AssignRolePermissionRequest;
import com.example.rarelystylebe.app.dtos.request.AssignUserRoleRequest;
import com.example.rarelystylebe.app.dtos.request.RoleRequest;
import com.example.rarelystylebe.app.dtos.response.AssignRolePermissionResponse;
import com.example.rarelystylebe.app.dtos.response.RoleResponse;
import com.example.rarelystylebe.domain.entities.Role;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);

    AssignRolePermissionResponse assignRolePermission(AssignRolePermissionRequest assignRolePermissionRequest);


    Role findByID(Long id);
}
