package com.example.rarelystylebe.domain.repositories;

import com.example.rarelystylebe.domain.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>, JpaSpecificationExecutor<Color> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM Color ")
    Long getNextSeq();

}
