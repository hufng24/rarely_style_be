package com.example.rarelystylebe.domain.repositories;


import com.example.rarelystylebe.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Size ")
    Long getNextSeq();
}
