package com.example.rarelystylebe.app.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRolePermissionRequest {

    @NotBlank(message = "Tên vai trò không được để trống")
    String name;

    @NotEmpty(message = "Vui lòng chọn quyền")
    Set<Long> permissionIds;
}
