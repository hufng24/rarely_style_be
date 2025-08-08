package com.example.rarelystylebe.domain.repositories;


import com.example.rarelystylebe.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Category ")
    Long getNextSeq();
}
