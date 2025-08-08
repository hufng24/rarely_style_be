package com.example.rarelystylebe.app.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQueryResponse {

    private String colorName;
    private Double price;
    private String productName;
}
