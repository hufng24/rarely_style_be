package com.example.rarelystylebe.domain.repositories;

import com.example.rarelystylebe.app.dtos.response.projection.MaterialProjection;
import com.example.rarelystylebe.domain.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Material ")
    Long getNextSeq();


//    @Query(value = "select mt.id as id , mt.name as name  from products.material mt where mt.id= :id", nativeQuery = true)
//    Optional<MaterialProjection> findByIdResponse(@Param("id") Long id);
}
