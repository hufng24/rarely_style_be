package com.example.rarelystylebe.domain.repositories.specification;

import com.example.rarelystylebe.app.dtos.filter.UserParam;
import com.example.rarelystylebe.domain.entities.User;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterSpec(UserParam param) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(param.getSearch())) {
                String keyword = "%" + param.getSearch().toLowerCase() + "%";

                Predicate fullNamePredicate = cb.like(cb.lower(root.get("fullName")), keyword);
                Predicate emailPredicate = cb.like(cb.lower(root.get("email")), keyword);

                // Sửa: thêm toLowerCase() cho phoneNumber
                Predicate phoneNumberPredicate = cb.like(cb.lower(root.get("phoneNumber")), keyword);

                predicates.add(cb.or(fullNamePredicate, emailPredicate, phoneNumberPredicate));
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
