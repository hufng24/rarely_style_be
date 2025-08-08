package com.example.rarelystylebe.domain.repositories;


import com.example.rarelystylebe.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Product ")
    Long getNextSeq();


}
