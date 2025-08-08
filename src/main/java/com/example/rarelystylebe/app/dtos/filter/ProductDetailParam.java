package com.example.rarelystylebe.app.dtos.filter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailParam {
    String code;

    String name;

    Double minPrice;

    Double maxPrice;

    Long categoryId;

    Long productId;

    Long brandId;

    Long materialId;

    Long sizeId;

    Long colorId;
}
