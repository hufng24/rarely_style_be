package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.SizeParam;
import com.example.rarelystylebe.domain.entities.Size;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SizeSpecification {
    public static Specification<Size> filterSpec(SizeParam param) {
        return (Root<Size> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
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
