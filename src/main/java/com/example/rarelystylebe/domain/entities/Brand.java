package com.example.rarelystylebe.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "brand", schema = "products")
@Where(clause = "is_deleted = false")
public class Brand extends BaseEntity {
    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted = Boolean.FALSE;
}
