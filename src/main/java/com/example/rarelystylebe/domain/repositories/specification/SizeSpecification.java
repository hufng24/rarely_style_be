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

            if (StringUtils.isNotBlank(param.getSearch())) {
                String search = "%" + param.getSearch().toLowerCase() + "%";

                Predicate namePredicate = cb.like(cb.lower(root.get("name")), search);

                predicate = cb.and(predicate, cb.or(namePredicate));
            }

            query.orderBy(cb.desc(root.get("id")));
            return predicate;
        };
    }
}
