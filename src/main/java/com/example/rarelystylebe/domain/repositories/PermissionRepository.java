package com.example.rarelystylebe.domain.repositories;

import com.example.rarelystylebe.domain.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
