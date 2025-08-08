package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.CategoryParam;
import com.example.rarelystylebe.domain.entities.Category;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> filterSpec(CategoryParam param) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + param.getName().toLowerCase() + "%"));
            }
            return predicate;
        };
    }
}
