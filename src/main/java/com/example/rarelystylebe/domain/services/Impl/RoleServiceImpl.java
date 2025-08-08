package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.request.AssignRolePermissionRequest;
import com.example.rarelystylebe.app.dtos.request.RoleRequest;
import com.example.rarelystylebe.app.dtos.response.AssignRolePermissionResponse;
import com.example.rarelystylebe.app.dtos.response.PermissionResponse;
import com.example.rarelystylebe.app.dtos.response.RoleResponse;
import com.example.rarelystylebe.domain.entities.Permission;
import com.example.rarelystylebe.domain.entities.Role;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.PermissionRepository;
import com.example.rarelystylebe.domain.repositories.RoleRepository;
import com.example.rarelystylebe.domain.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
        roleRepository.save(role);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(roleRequest.getName());
        return roleResponse;
    }


    @Override
    public AssignRolePermissionResponse assignRolePermission(AssignRolePermissionRequest roleRequest) {
       try {
           Role role = roleRepository.findByName(roleRequest.getName())
                   .orElseThrow(
                           () -> new ResponseException(HttpStatus.NOT_FOUND, "Role not found: " + roleRequest.getName()));
           Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequest.getPermissionIds()));
           role.setPermissions(permissions);
           Role saved = roleRepository.save(role);
           Set<PermissionResponse> permissionResponses = permissions.stream().map(
                   p -> new PermissionResponse(p.getId(), p.getName())).collect(Collectors.toSet());

           return new AssignRolePermissionResponse(saved.getId(), saved.getName(), permissionResponses);
       }catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public Role findByID(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));
    }

}

