package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.ColorParam;
import com.example.rarelystylebe.app.dtos.request.ColorRequest;
import com.example.rarelystylebe.app.dtos.response.ColorResponse;
import com.example.rarelystylebe.domain.entities.Color;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ColorService {

    ColorResponse create(ColorRequest colorRequest);

    ColorResponse update(Long id,ColorRequest colorRequest) throws JsonMappingException;

    void delete(Long id);

    Color findById(Long id);

    Page<ColorResponse> filter(ColorParam colorParam, Pageable pageable);
}
