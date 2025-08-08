package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.ColorParam;
import com.example.rarelystylebe.app.dtos.request.ColorRequest;
import com.example.rarelystylebe.app.dtos.response.ColorResponse;
import com.example.rarelystylebe.domain.entities.Color;
import com.example.rarelystylebe.domain.services.ColorService;
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
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
@Tag(name = "Api màu", description = "Quản lý các màu sắc trong hệ thống")
public class ColorController {

    private final ColorService colorService;

    @Operation(summary = "Lọc danh sách màu", description = "Trả về danh sách màu theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<ColorResponse> filter(@Parameter(description = "Điều kiện lọc màu sắc") ColorParam colorParam, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(colorService.filter(colorParam, pageable));
    }

    @Operation(summary = "Tạo mới màu sắc", description = "Tạo một màu sắc mới trong hệ thống")
    @PostMapping
    public ColorResponse create(@Parameter(description = "Thông tin màu sắc") @Valid @RequestBody ColorRequest req) {
        return colorService.create(req);
    }

    @Operation(summary = "Cập nhật màu sắc", description = "Cập nhật thông tin màu sắc dựa trên ID")
    @PutMapping("/{id}")
    public ColorResponse update(@Parameter(description = "ID của màu sắc cần cập nhật") @PathVariable Long id, @Parameter(description = "Thông tin mới của màu sắc") @Valid @RequestBody ColorRequest req) throws JsonMappingException {
        return colorService.update(id, req);
    }

    @Operation(summary = "Lấy chi tiết màu sắc", description = "Trả về thông tin chi tiết của một màu sắc theo ID")
    @GetMapping("/{id}")
    public Color findByID(@Parameter(description = "ID của màu sắc cần truy xuất") @PathVariable Long id) {
        return colorService.findById(id);
    }

    @Operation(summary = "Xóa màu sắc", description = "Xóa một màu sắc khỏi hệ thống theo ID")
    @DeleteMapping("/{id}")
    public void deleteByID(@Parameter(description = "ID của màu sắc cần xóa") @PathVariable Long id) {
        colorService.delete(id);
    }
}
