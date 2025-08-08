package com.example.rarelystylebe.app.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BrandResponse extends BaseResponse {
    private String code;

    private String name;

    private Boolean isDeleted;
}
