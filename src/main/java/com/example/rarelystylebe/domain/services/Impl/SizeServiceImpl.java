package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.SizeParam;
import com.example.rarelystylebe.app.dtos.request.SizeRequest;
import com.example.rarelystylebe.app.dtos.response.SizeResponse;
import com.example.rarelystylebe.domain.entities.Size;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.SizeRepository;
import com.example.rarelystylebe.domain.repositories.specification.SizeSpecification;
import com.example.rarelystylebe.domain.services.SizeService;
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
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "SZ";

    @Override
    @Transactional
    public SizeResponse create(SizeRequest sizeRequest) {
        Size size = objectMapper.convertValue(sizeRequest, Size.class);
        size.setCode(PREFIX_CODE + sizeRepository.getNextSeq());
        return objectMapper.convertValue(sizeRepository.save(size), SizeResponse.class);
    }

    @Override
    @Transactional
    public SizeResponse update(Long id, SizeRequest sizeRequest) throws JsonMappingException {
        Size size = findById(id);
        objectMapper.updateValue(size, sizeRequest);
        return objectMapper.convertValue(sizeRepository.save(size), SizeResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Size size = findById(id);
        size.setIsDeleted(true);
        sizeRepository.save(size);
    }

    @Override
    public Size findById(Long id) {
        return sizeRepository.findById(id).orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.SIZE_NOT_FOUND));
    }

    @Override
    public Page<SizeResponse> filter(SizeParam sizeParam, Pageable pageable) {
        Specification sizeSpec = SizeSpecification.filterSpec(sizeParam);
        Page<Size> sizePage = sizeRepository.findAll(sizeSpec, pageable);
        return sizePage.map(size -> objectMapper.convertValue(size, SizeResponse.class));
    }

}
