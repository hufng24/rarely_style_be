package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.CategoryParam;
import com.example.rarelystylebe.app.dtos.request.CategoryRequest;
import com.example.rarelystylebe.app.dtos.response.CategoryResponse;
import com.example.rarelystylebe.domain.entities.Category;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.CategoryRepository;
import com.example.rarelystylebe.domain.repositories.specification.CategorySpecification;
import com.example.rarelystylebe.domain.services.CategoryService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category category = objectMapper.convertValue(categoryRequest, Category.class);
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) throws JsonMappingException {
        Category category = findById(id);
        objectMapper.updateValue(category, categoryRequest);
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = findById(id);
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.CATEGORY_NOT_FOUND));
    }

    @Override
    public Page<CategoryResponse> filter(CategoryParam categoryParam, Pageable pageable) {
        Specification categorySpec = CategorySpecification.filterSpec(categoryParam);
        Page<Category> categoryPage = categoryRepository.findAll(categorySpec, pageable);
        return categoryPage.map(category -> objectMapper.convertValue(category, CategoryResponse.class));
    }

}
