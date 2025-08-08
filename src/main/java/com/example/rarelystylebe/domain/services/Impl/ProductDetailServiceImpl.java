package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.ProductDetailParam;
import com.example.rarelystylebe.app.dtos.request.ProductDetailRequest;
import com.example.rarelystylebe.app.dtos.request.ProductRequest;
import com.example.rarelystylebe.app.dtos.response.ProductDetailResponse;
import com.example.rarelystylebe.domain.entities.ProductDetail;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.ProductDetailRepository;
import com.example.rarelystylebe.domain.repositories.specification.ProductDetailSpecification;
import com.example.rarelystylebe.domain.services.*;
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
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    private final CategoryService categoryService;

    private final ProductService productService;

    private final ColorService colorService;

    private final SizeService sizeService;

    private final MaterialService materialService;

    private final BrandService brandService;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "PRD";

    @Override
    @Transactional
    public ProductDetailResponse create(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = objectMapper.convertValue(productDetailRequest, ProductDetail.class);
        productDetail.setCode(PREFIX_CODE + productDetailRepository.getNextSeq());
        setEntityRel(productDetail, productDetailRequest);
        return objectMapper.convertValue(productDetailRepository.save(productDetail), ProductDetailResponse.class);
    }

    @Override
    @Transactional
    public ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) throws JsonMappingException {
        ProductDetail productDetail = findById(id);
        objectMapper.updateValue(productDetail, productDetailRequest);
        setEntityRel(productDetail, productDetailRequest);
        return objectMapper.convertValue(productDetailRepository.save(productDetail), ProductDetailResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductDetail productDetail = findById(id);
        productDetail.setIsDeleted(true);
        productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail findById(Long id) {
        return productDetailRepository.findById(id).orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.PRODUCT_DETAIL_NOT_FOUND));
    }

    @Override
    public Page<ProductDetailResponse> filter(ProductDetailParam productDetailParam, Pageable pageable) {
        Specification<ProductDetail> productDetailSpec = ProductDetailSpecification.filterSpec(productDetailParam);
        Page<ProductDetail> productDetailPage = productDetailRepository.findAll(productDetailSpec, pageable);
        return productDetailPage.map(productDetail -> objectMapper.convertValue(productDetail, ProductDetailResponse.class));
    }

    private void setEntityRel(ProductDetail productDetail, ProductDetailRequest productDetailRequest) {
        productDetail.setProduct(productService.findById(productDetailRequest.getProductId()));
        productDetail.setCategory(categoryService.findById(productDetailRequest.getCategoryId()));
        productDetail.setBrand(brandService.findById(productDetailRequest.getBrandId()));
        productDetail.setMaterial(materialService.findById(productDetailRequest.getMaterialId()));
        productDetail.setSize(sizeService.findById(productDetailRequest.getSizeId()));
        productDetail.setColor(colorService.findById(productDetailRequest.getColorId()));
    }

}
