package com.example.rarelystylebe.app.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRolePermissionResponse {
    Long id;
    String name;
    Set<PermissionResponse> permissions;
}
