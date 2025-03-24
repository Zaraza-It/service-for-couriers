package org.example.market.repository;

import org.example.market.dto.response.ResponseSoldProduct;
import org.example.market.entity.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldProductRepository  extends JpaRepository<SoldProduct, Long> {
    @NativeQuery("SELECT quantity FROM sold_product WHERE id_seller = id")
    List<ResponseSoldProduct> findSoldByIdUser(@Param("id") Long id);
}
