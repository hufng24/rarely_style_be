package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.BrandParam;
import com.example.rarelystylebe.app.dtos.request.BrandRequest;
import com.example.rarelystylebe.app.dtos.response.BrandResponse;
import com.example.rarelystylebe.domain.entities.Brand;
import com.example.rarelystylebe.domain.services.BrandService;
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
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Api thương hiệu", description = "Quản lý các thương hiệu sản phẩm")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "Lọc danh sách thương hiệu", description = "Trả về danh sách thương hiệu dựa trên điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<BrandResponse> filter(@Parameter(description = "Điều kiện lọc thương hiệu") BrandParam brandParam, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(brandService.filter(brandParam, pageable));
    }

    @Operation(summary = "Tạo mới thương hiệu", description = "Tạo một thương hiệu mới trong hệ thống")
    @PostMapping
    public BrandResponse create(@Parameter(description = "Thông tin thương hiệu cần tạo") @Valid @RequestBody BrandRequest brandRequest) {
        return brandService.create(brandRequest);
    }

    @Operation(summary = "Cập nhật thương hiệu", description = "Cập nhật thông tin thương hiệu theo ID")
    @PutMapping("/{id}")
    public BrandResponse update(@Parameter(description = "ID của thương hiệu") @PathVariable Long id, @Parameter(description = "Thông tin mới của thương hiệu") @Valid @RequestBody BrandRequest brandRequest) throws JsonMappingException {
        return brandService.update(id, brandRequest);
    }

    @Operation(summary = "Lấy chi tiết thương hiệu", description = "Lấy thông tin chi tiết của thương hiệu theo ID")
    @GetMapping("/{id}")
    public Brand findById(@Parameter(description = "ID của thương hiệu") @PathVariable Long id) {
        return brandService.findById(id);
    }

    @Operation(summary = "Xóa thương hiệu", description = "Xóa một thương hiệu khỏi hệ thống theo ID")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "ID của thương hiệu cần xóa") @PathVariable Long id) {
        brandService.delete(id);
    }
}
