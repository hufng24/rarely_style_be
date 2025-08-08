package com.example.rarelystylebe.domain.repositories;

import com.example.rarelystylebe.domain.entities.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Brand")
    Long getNextSeq();
}
