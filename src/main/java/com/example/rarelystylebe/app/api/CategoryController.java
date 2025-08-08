package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.CategoryParam;
import com.example.rarelystylebe.app.dtos.request.CategoryRequest;
import com.example.rarelystylebe.app.dtos.response.CategoryResponse;
import com.example.rarelystylebe.domain.entities.Category;
import com.example.rarelystylebe.domain.services.CategoryService;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Api danh mục", description = "Quản lý danh mục sản phẩm")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Lọc danh mục", description = "Trả về danh sách danh mục theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<CategoryResponse> filter(@Parameter(description = "Điều kiện lọc danh mục") CategoryParam categoryParam, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(categoryService.filter(categoryParam, pageable));
    }

    @Operation(summary = "Tạo mới danh mục", description = "Tạo một danh mục mới trong hệ thống")
    @PostMapping
    public CategoryResponse create(@Parameter(description = "Thông tin danh mục") @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.create(categoryRequest);
    }

    @Operation(summary = "Cập nhật danh mục", description = "Cập nhật thông tin của một danh mục theo ID")
    @PutMapping("/{id}")
    public CategoryResponse update(@Parameter(description = "ID danh mục cần cập nhật") @PathVariable Long id, @Parameter(description = "Thông tin mới của danh mục") @Valid @RequestBody CategoryRequest categoryRequest) throws JsonMappingException {
        return categoryService.update(id, categoryRequest);
    }

    @Operation(summary = "Lấy chi tiết danh mục", description = "Trả về thông tin chi tiết của một danh mục theo ID")
    @GetMapping("/{id}")
    public Category findById(@Parameter(description = "ID của danh mục cần truy xuất") @PathVariable Long id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "Xóa danh mục", description = "Xóa một danh mục khỏi hệ thống theo ID")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "ID của danh mục cần xóa") @PathVariable Long id) {
        categoryService.delete(id);
    }
}
