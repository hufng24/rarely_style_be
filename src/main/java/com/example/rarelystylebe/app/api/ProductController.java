package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.ProductParam;
import com.example.rarelystylebe.app.dtos.request.ProductRequest;
import com.example.rarelystylebe.app.dtos.response.ProductResponse;
import com.example.rarelystylebe.domain.annotation.RequirePermissions;
import com.example.rarelystylebe.domain.entities.Product;
import com.example.rarelystylebe.domain.services.ProductService;
import com.example.rarelystylebe.domain.utils.PageUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Api sản phẩm", description = "Quản lý danh sách và thông tin sản phẩm")
public class ProductController {
    private final ProductService productService;


    @Operation(summary = "Lọc sản phẩm", description = "Trả về danh sách sản phẩm theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<ProductResponse> filter(@Parameter(description = "Các điều kiện lọc sản phẩm") ProductParam productParam, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(productService.filter(productParam, pageable));
    }

    @Operation(summary = "Tạo mới sản phẩm", description = "Tạo một bản ghi sản phẩm mới với thông tin được cung cấp")
    @PostMapping
    @RequirePermissions({"insert_product"})
    public ProductResponse create(@Parameter(description = "Thông tin sản phẩm") @Valid @RequestBody ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật thông tin sản phẩm theo ID")
    @PutMapping("/{id}")
    public ProductResponse update(@Parameter(description = "ID của sản phẩm cần cập nhật") @PathVariable Long id,
                                  @Parameter(description = "Thông tin sản phẩm cập nhật") @Valid @RequestBody ProductRequest productRequest) throws JsonMappingException {
        return productService.update(id, productRequest);
    }

    @Operation(summary = "Xóa sản phẩm", description = "Xóa một sản phẩm khỏi hệ thống dựa vào ID")
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "ID của sản phẩm cần xóa") @PathVariable Long id) {
        productService.delete(id);
    }

    @Operation(summary = "Lấy thông tin sản phẩm", description = "Trả về thông tin chi tiết của một sản phẩm theo ID")
    @GetMapping("/{id}")
    public Product findById(@Parameter(description = "ID của sản phẩm cần truy xuất") @PathVariable Long id) {
        return productService.findById(id);
    }
}
