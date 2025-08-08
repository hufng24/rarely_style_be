package com.example.rarelystylebe.app.api;

import com.example.rarelystylebe.app.dtos.filter.MaterialParam;
import com.example.rarelystylebe.app.dtos.request.MaterialRequest;
import com.example.rarelystylebe.app.dtos.response.MaterialResponse;
import com.example.rarelystylebe.domain.entities.Material;
import com.example.rarelystylebe.domain.services.MaterialService;
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
@RequestMapping("/api/v1/materials")
@RequiredArgsConstructor
@Tag(name = "Api chất liệu", description = "Quản lý các chất liệu sản phẩm")
public class MaterialController {

    private final MaterialService materialService;

    @Operation(summary = "Lọc danh sách chất liệu", description = "Trả về danh sách chất liệu theo điều kiện lọc và phân trang")
    @GetMapping
    public PageUtils<MaterialResponse> filter(@Parameter(description = "Các điều kiện lọc chất liệu") MaterialParam param, @Parameter(description = "Thông tin phân trang") Pageable pageable) {
        return new PageUtils<>(materialService.filter(param, pageable));
    }

    @Operation(summary = "Tạo mới chất liệu", description = "Tạo một bản ghi chất liệu mới từ thông tin đầu vào")
    @PostMapping
    public MaterialResponse create(@Parameter(description = "Thông tin chất liệu cần tạo") @Valid @RequestBody MaterialRequest req) {
        return materialService.create(req);
    }

    @Operation(summary = "Cập nhật chất liệu", description = "Cập nhật thông tin của một chất liệu theo ID")
    @PutMapping("/{id}")
    public MaterialResponse update(@Parameter(description = "ID của chất liệu cần cập nhật") @PathVariable Long id, @Parameter(description = "Thông tin mới của chất liệu") @Valid @RequestBody MaterialRequest req) throws JsonMappingException {
        return materialService.update(id, req);
    }

    @Operation(summary = "Lấy chi tiết chất liệu theo ID", description = "Trả về thông tin chi tiết của chất liệu theo ID")
    @GetMapping("/{id}")
    public Material findByID(@Parameter(description = "ID của chất liệu cần truy xuất") @PathVariable Long id) {
        return materialService.findById(id);
    }

    @Operation(summary = "Xóa chất liệu", description = "Xóa chất liệu khỏi hệ thống theo ID")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "ID của chất liệu cần xóa") @PathVariable Long id) {
        materialService.delete(id);
    }
}
