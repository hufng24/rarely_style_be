package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.ProductParam;
import com.example.rarelystylebe.app.dtos.request.ProductRequest;
import com.example.rarelystylebe.app.dtos.response.ProductResponse;
import com.example.rarelystylebe.domain.entities.Product;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest) throws JsonMappingException;

    void delete(Long id);

    Product findById(Long id);

    Page<ProductResponse> filter(ProductParam productParam, Pageable pageable);
}
