package com.example.rarelystylebe.app.dtos.response;

import com.example.rarelystylebe.domain.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailResponse extends BaseResponse {
    String code;

    String name;

    Integer quantity;

    Double price;

    List<String> image;

    String description;

    Boolean isDeleted;

    Product product;

    Category category;

    Material material;

    Brand Brand;

    Color color;

    Size size;


}
