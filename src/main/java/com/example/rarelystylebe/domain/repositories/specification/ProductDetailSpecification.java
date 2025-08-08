package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.ProductDetailParam;
import com.example.rarelystylebe.domain.entities.ProductDetail;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductDetailSpecification {
    public static Specification<ProductDetail> filterSpec(ProductDetailParam param) {
        return (Root<ProductDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + param.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getCode())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("code")), "%" + param.getCode().toLowerCase() + "%"));
            }

            if (param.getMinPrice() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), param.getMinPrice()));
            }

            if (param.getMaxPrice() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), param.getMaxPrice()));
            }

            if (param.getCategoryId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("categoryId"), param.getCategoryId()));
            }

            if (param.getBrandId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("brandId"), param.getBrandId()));
            }

            if (param.getMaterialId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("materialId"), param.getMaterialId()));
            }

            if (param.getSizeId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("sizeId"), param.getSizeId()));
            }

            if (param.getColorId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("colorId"), param.getColorId()));
            }

            predicate = cb.and(predicate, cb.isFalse(root.get("isDeleted"))); // loại trừ bản ghi đã xoá


            return predicate;
        };
    }
}
