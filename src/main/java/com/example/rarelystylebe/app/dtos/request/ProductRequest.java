package com.example.rarelystylebe.app.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @NotBlank(message = "Tên không được để trống")
  private String name;

  private String description;
}
