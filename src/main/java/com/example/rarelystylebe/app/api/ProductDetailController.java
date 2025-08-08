package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.ProductDetailParam;
import com.example.rarelystylebe.app.dtos.request.ProductDetailRequest;
import com.example.rarelystylebe.app.dtos.response.ProductDetailResponse;
import com.example.rarelystylebe.domain.entities.ProductDetail;
import com.example.rarelystylebe.domain.services.ProductDetailService;
import com.example.rarelystylebe.domain.utils.PageUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-details")
@RequiredArgsConstructor
@Tag(name = "Api sản phẩm chi tiết", description = "Quản lý thông tin sản phẩm chi tiết")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @Operation(summary = "Lọc sản phẩm chi tiết", description = "Trả về danh sách sản phẩm chi tiết theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<ProductDetailResponse> filter(@Parameter(description = "Các tham số lọc sản phẩm chi tiết") ProductDetailParam productDetailParam, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(productDetailService.filter(productDetailParam, pageable));
    }

    @Operation(summary = "Tạo mới sản phẩm chi tiết", description = "Tạo một bản ghi sản phẩm chi tiết mới dựa trên request gửi lên")
    @PostMapping
    public ProductDetailResponse create(@Parameter(description = "Thông tin sản phẩm chi tiết") @Valid @RequestBody ProductDetailRequest productDetailRequest) {
        return productDetailService.create(productDetailRequest);
    }

    @Operation(summary = "Cập nhật sản phẩm chi tiết", description = "Cập nhật thông tin của sản phẩm chi tiết theo ID")
    @PutMapping("/{id}")
    public ProductDetailResponse update(@Parameter(description = "ID của sản phẩm chi tiết") @PathVariable Long id, @Parameter(description = "Thông tin mới của sản phẩm chi tiết") @Valid @RequestBody ProductDetailRequest productDetailRequest) throws JsonMappingException {
        return productDetailService.update(id, productDetailRequest);
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo ID", description = "Trả về chi tiết sản phẩm dựa trên ID")
    @GetMapping("/{id}")
    public ProductDetail findById(@Parameter(description = "ID của sản phẩm chi tiết") @PathVariable Long id) {
        return productDetailService.findById(id);
    }

    @Operation(summary = "Xóa sản phẩm chi tiết theo ID", description = "Xóa bản ghi sản phẩm chi tiết theo ID")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "ID của sản phẩm chi tiết") @PathVariable Long id) {
        productDetailService.delete(id);
    }
}
