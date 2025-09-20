package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.ColorParam;
import com.example.rarelystylebe.domain.entities.Color;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ColorSpecification {
    public static Specification<Color> filterSpec(ColorParam param) {
        return (Root<Color> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getSearch())) {
                String search = "%" + param.getSearch().toLowerCase() + "%";

                Predicate namePredicate = cb.like(cb.lower(root.get("name")), search);
                Predicate codePredicate = cb.like(cb.lower(root.get("code")), search);

                // OR giữa name và code
                predicate = cb.and(predicate, cb.or(namePredicate, codePredicate));
            }

            // Thêm ORDER BY id DESC
            query.orderBy(cb.desc(root.get("id")));

            return predicate;
        };
    }
}
