package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.ProductParam;
import com.example.rarelystylebe.domain.entities.Product;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> filterSpec(ProductParam param) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + param.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getCode())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("code")), "%" + param.getCode().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
