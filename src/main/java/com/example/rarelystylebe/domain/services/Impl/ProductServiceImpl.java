package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.ProductParam;
import com.example.rarelystylebe.app.dtos.request.ProductRequest;
import com.example.rarelystylebe.app.dtos.response.ProductDetailResponse;
import com.example.rarelystylebe.app.dtos.response.ProductResponse;
import com.example.rarelystylebe.domain.entities.Product;
import com.example.rarelystylebe.domain.entities.ProductDetail;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.ProductDetailRepository;
import com.example.rarelystylebe.domain.repositories.ProductRepository;
import com.example.rarelystylebe.domain.repositories.specification.ProductSpecification;
import com.example.rarelystylebe.domain.services.ProductService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "PR";

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = objectMapper.convertValue(productRequest, Product.class);
        product.setCode(PREFIX_CODE + productRepository.getNextSeq());
        ProductResponse pd = objectMapper.convertValue(productRepository.save(product), ProductResponse.class);
        pd.setProductDetails(productDetailRepository.findByProductId(product.getId()));
        return pd ;
    }



    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) throws JsonMappingException {
        Product product = findById(id);
        objectMapper.updateValue(id,productRequest);
        return objectMapper.convertValue(productRepository.save(product), ProductResponse.class);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id);
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.BRAND_NOT_FOUND));
    }

    @Override
    public Page<ProductResponse> filter(ProductParam productParam, Pageable pageable) {
        Specification<Product> productSpec = ProductSpecification.filterSpec(productParam);
        Page<Product> productPage = productRepository.findAll(productSpec, pageable);

        return productPage.map(product -> {
            ProductResponse response = objectMapper.convertValue(product, ProductResponse.class);
            List<ProductDetail> details = productDetailRepository.findByProductId(product.getId());
            response.setProductDetails(details);
            return response;
        });
    }

}
