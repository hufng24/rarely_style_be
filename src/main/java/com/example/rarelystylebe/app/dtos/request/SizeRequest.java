package com.example.rarelystylebe.app.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeRequest {
    @NotBlank(message = "Kích thước không được để trống")
    private String name;
}
