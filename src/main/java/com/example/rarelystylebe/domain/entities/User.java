package com.example.rarelystylebe.domain.entities;

import com.example.rarelystylebe.domain.enums.Gender;
import com.example.rarelystylebe.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user", schema = "users")
@Where(clause = "is_deleted = false")
public class User extends BaseEntity {

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "password")
    String password;

    @Column(name = "email", unique = true)
    String email;

    @Column(nullable = false, name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(nullable = false, name = "avatar")
    String avatar;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false, name = "is_admin")
    Boolean isAdmin = Boolean.FALSE;

    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted = Boolean.FALSE;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "rel_user_roles",schema = "users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
