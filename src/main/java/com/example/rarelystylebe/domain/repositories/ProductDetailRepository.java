package com.example.rarelystylebe.domain.repositories;

import com.example.rarelystylebe.domain.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> , JpaSpecificationExecutor<ProductDetail> {
    @Query("SELECT COALESCE(MAX(id), 0) + 1 FROM ProductDetail ")
    Long getNextSeq();
    // HQL QUERY
//    @Query(value = "SELECT new com.example.rarelystylebe.app.dtos.response.TestQueryResponse(c.name , pd.price , pd.name ) " +
//            "from ProductDetail pd JOIN Color c on pd.colorId = c.id")
//    TestQueryResponse findProductTest(Long id);
//
//    // NATIVE QUERY
//    @Query(value = "SELECT c.name AS color_name, pd.price AS price_product, pd.name AS product_name " +
//            "FROM products.product_detail pd " +
//            "JOIN products.color c ON c.id = pd.color_id " +
//            "WHERE pd.id = :id", nativeQuery = true)
//    ProductDetailProjection findByIdPro(@Param("id") Long id);

    List<ProductDetail> findByProductId(Long productId);
}
