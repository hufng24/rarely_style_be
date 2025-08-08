package com.example.rarelystylebe.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product", schema = "products")
@Where(clause = "is_deleted = false")
public class Product extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "description")
    String description;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted = Boolean.FALSE;

}

