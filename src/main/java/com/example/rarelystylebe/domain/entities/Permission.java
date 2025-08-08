package com.example.rarelystylebe.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "permission", schema = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    Long id;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false , name = "value")
    String value;

    @ManyToMany(mappedBy = "permissions")
    Set<Role> roles;
}
