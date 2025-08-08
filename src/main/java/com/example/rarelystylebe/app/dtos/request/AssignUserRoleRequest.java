package com.example.rarelystylebe.app.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignUserRoleRequest {

//    @NotBlank(message = "Email không được để trống")
//    @Email(message = "Email không đúng định dạng")
//    String email;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Khong dung dinh dang")
    String email;

    @NotEmpty(message = "Danh sách roleId không được rỗng")
    List<Long> roleIds;
}
