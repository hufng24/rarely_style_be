package com.example.rarelystylebe.app.dtos.filter;

import com.example.rarelystylebe.domain.enums.UserStatus;
import lombok.Data;

@Data
public class UserParam {

    private String name;

    private String email;

    private Boolean isAdmin;

    private UserStatus status;
}
