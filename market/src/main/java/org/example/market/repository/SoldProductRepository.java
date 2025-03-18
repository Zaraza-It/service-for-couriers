package org.example.market.repository;

import org.example.market.entity.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldProductRepository  extends JpaRepository<SoldProduct, Long> {
}
