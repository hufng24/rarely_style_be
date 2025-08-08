package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.ProductDetailParam;
import com.example.rarelystylebe.app.dtos.request.ProductDetailRequest;
import com.example.rarelystylebe.app.dtos.request.ProductRequest;
import com.example.rarelystylebe.app.dtos.response.ProductDetailResponse;
import com.example.rarelystylebe.domain.entities.ProductDetail;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailService {
    ProductDetailResponse create(ProductDetailRequest productDetailRequest);

    ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) throws JsonMappingException;

    void delete(Long id);

    ProductDetail findById(Long id);

    Page<ProductDetailResponse> filter(ProductDetailParam productDetailParam, Pageable pageable);
}
