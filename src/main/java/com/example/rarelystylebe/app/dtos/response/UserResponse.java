// UserResponse.java
package com.example.rarelystylebe.app.dtos.response;

import com.example.rarelystylebe.domain.enums.Gender;
import com.example.rarelystylebe.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse {
    private Long id;
    private String fullName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private Boolean isAdmin;
    private String gender;
    private String status;
    private Boolean isDeleted;

    private List<String> roles;
}
