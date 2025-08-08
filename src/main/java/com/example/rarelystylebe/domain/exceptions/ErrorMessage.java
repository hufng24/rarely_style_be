package com.example.rarelystylebe.domain.exceptions;

import com.eps.shared.models.exceptions.BaseErrorMessage;

public enum ErrorMessage implements BaseErrorMessage {
    MATERIAL_NOT_FOUND("Chất liệu không được để trống"),
    SIZE_NOT_FOUND("Kích thước không được để trống"),
    COLOR_NOT_FOUND("Màu không được để trống"),
    BRAND_NOT_FOUND("Thương hiệu không được để trống"),
    PRODUCT_NOT_FOUND("Sản phẩm không được để trống"),
    PRODUCT_DETAIL_NOT_FOUND("Sản phẩm chi tiet không được để trống"),
    CATEGORY_NOT_FOUND("Danh mục không được để trống"),
    USER_NOT_FOUND("Không tìm thấy user"),
    ROLE_NOT_FOUND("K tìm thấy role");
    public String val;

    private ErrorMessage(String label) {
        val = label;
    }

    @Override
    public String val() {
        return val;
    }
}
