package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.BrandParam;
import com.example.rarelystylebe.app.dtos.request.BrandRequest;
import com.example.rarelystylebe.app.dtos.response.BrandResponse;
import com.example.rarelystylebe.domain.entities.Brand;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    BrandResponse create(BrandRequest brandRequest);

    BrandResponse update(Long id, BrandRequest brandRequest) throws JsonMappingException;

    void delete(Long id);

    Brand findById(Long id);

    Page<BrandResponse> filter(BrandParam brandParam, Pageable pageable);
}
