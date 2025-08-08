package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.CategoryParam;
import com.example.rarelystylebe.app.dtos.request.CategoryRequest;
import com.example.rarelystylebe.app.dtos.response.CategoryResponse;
import com.example.rarelystylebe.domain.entities.Category;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(Long id,CategoryRequest categoryRequest) throws JsonMappingException;

    void delete(Long id);

    Category findById(Long id);

    Page<CategoryResponse> filter(CategoryParam categoryParam, Pageable pageable);
}
