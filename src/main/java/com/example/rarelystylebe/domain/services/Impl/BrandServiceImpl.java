package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.BrandParam;
import com.example.rarelystylebe.app.dtos.request.BrandRequest;
import com.example.rarelystylebe.app.dtos.response.BrandResponse;
import com.example.rarelystylebe.domain.entities.Brand;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.BrandRepository;
import com.example.rarelystylebe.domain.repositories.specification.BrandSpecification;
import com.example.rarelystylebe.domain.services.BrandService;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "BR";

    @Override
    @Transactional
    public BrandResponse create(BrandRequest brandRequest) {
        Brand brand = objectMapper.convertValue(brandRequest, Brand.class);
        brand.setCode(PREFIX_CODE + brandRepository.getNextSeq());
        return objectMapper.convertValue(brandRepository.save(brand), BrandResponse.class);
    }

    @Override
    @Transactional
    public BrandResponse update(Long id, BrandRequest brandRequest) throws JsonMappingException {
        Brand brand = findById(id);
        objectMapper.convertValue(brandRequest, Brand.class);
        return objectMapper.convertValue(brandRepository.save(brand), BrandResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Brand brand = findById(id);
        brand.setIsDeleted(true);
        brandRepository.save(brand);
    }

    @Override
    public Brand findById(Long id) {
        return  brandRepository.findById(id).orElseThrow(()-> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.BRAND_NOT_FOUND));
    }

    @Override
    public Page<BrandResponse> filter(BrandParam brandParam, Pageable pageable) {
        Specification brandSpec = BrandSpecification.filterSpec(brandParam);
        Page<Brand> brandPage = brandRepository.findAll(brandSpec, pageable);
        return brandPage.map(brand -> objectMapper.convertValue(brand, BrandResponse.class));
    }
}
