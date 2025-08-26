package com.example.rarelystylebe.app.dtos.request;

import com.example.rarelystylebe.domain.enums.Gender;
import com.example.rarelystylebe.domain.enums.UserStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Tên không được để trống")
    private String fullName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu ít nhất 8 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Giới tính không được null")
    private Gender gender;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String phone_number;

    @NotBlank(message = "Avatar không được để trống")
    private String avatar;

    private UserStatus status = UserStatus.ACTIVE;

    private Boolean isAdmin = Boolean.FALSE;

    @NotEmpty(message = "Phải chọn ít nhất 1 quyền")
    private Set<Long> roleIds;
}
