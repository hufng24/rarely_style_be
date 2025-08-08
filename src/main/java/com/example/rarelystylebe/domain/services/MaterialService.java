package com.example.rarelystylebe.domain.services;


import com.example.rarelystylebe.app.dtos.filter.MaterialParam;
import com.example.rarelystylebe.app.dtos.request.MaterialRequest;
import com.example.rarelystylebe.app.dtos.response.MaterialResponse;
import com.example.rarelystylebe.app.dtos.response.projection.MaterialProjection;
import com.example.rarelystylebe.domain.entities.Material;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialService {
    MaterialResponse create(MaterialRequest materialRequest);

    MaterialResponse update(Long id,MaterialRequest materialRequest) throws JsonMappingException;

    void delete(Long id);

    Material findById(Long id);

//    MaterialProjection findProjectionById(Long id);

    Page<MaterialResponse> filter(MaterialParam materialParam, Pageable pageable);
}
