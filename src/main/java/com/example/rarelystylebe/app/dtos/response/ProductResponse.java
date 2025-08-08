package com.example.rarelystylebe.app.dtos.response;

import com.example.rarelystylebe.domain.entities.Product;
import com.example.rarelystylebe.domain.entities.ProductDetail;
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
public class ProductResponse extends BaseResponse {

  String code;

  String name;

  String description;

  List<ProductDetail> productDetails;

  Boolean isDeleted;
}
