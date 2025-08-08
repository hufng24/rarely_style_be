package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.filter.SizeParam;
import com.example.rarelystylebe.app.dtos.request.SizeRequest;
import com.example.rarelystylebe.app.dtos.response.SizeResponse;
import com.example.rarelystylebe.domain.entities.Size;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SizeService {

    SizeResponse create(SizeRequest sizeRequest);

    SizeResponse update(Long id, SizeRequest sizeRequest) throws JsonMappingException;

    void delete(Long id);

    Size findById(Long id);

    Page<SizeResponse> filter(SizeParam sizeParam, Pageable pageable);
}
