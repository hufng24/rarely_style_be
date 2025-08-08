package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.SizeParam;
import com.example.rarelystylebe.app.dtos.request.SizeRequest;
import com.example.rarelystylebe.app.dtos.response.SizeResponse;
import com.example.rarelystylebe.domain.entities.Size;
import com.example.rarelystylebe.domain.services.SizeService;
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
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
@Tag(name = "Api kích thước", description = "Quản lý kích thước sản phẩm (S, M, L, XL,...)")
public class SizeController {

    private final SizeService sizeService;

    @Operation(summary = "Lọc danh sách kích thước", description = "Trả về danh sách các kích thước theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<SizeResponse> filter(@Parameter(description = "Điều kiện lọc kích thước") SizeParam param, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(sizeService.filter(param, pageable));
    }

    @Operation(summary = "Tạo mới kích thước", description = "Tạo một kích thước mới trong hệ thống")
    @PostMapping
    public SizeResponse create(@Parameter(description = "Thông tin kích thước cần tạo") @Valid @RequestBody SizeRequest sizeRequest) {
        return sizeService.create(sizeRequest);
    }

    @Operation(summary = "Cập nhật kích thước", description = "Cập nhật thông tin kích thước dựa trên ID")
    @PutMapping("/{id}")
    public SizeResponse update(@Parameter(description = "ID của kích thước cần cập nhật") @PathVariable Long id, @Parameter(description = "Thông tin mới của kích thước") @Valid @RequestBody SizeRequest sizeRequest) throws JsonMappingException {
        return sizeService.update(id, sizeRequest);
    }

    @Operation(summary = "Lấy thông tin kích thước theo ID", description = "Lấy chi tiết kích thước sản phẩm theo ID")
    @GetMapping("/{id}")
    public Size findById(@Parameter(description = "ID của kích thước") @PathVariable Long id) {
        return sizeService.findById(id);
    }

    @Operation(summary = "Xóa kích thước", description = "Xóa một kích thước theo ID khỏi hệ thống")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "ID của kích thước cần xóa") @PathVariable Long id) {
        sizeService.delete(id);
    }
}
