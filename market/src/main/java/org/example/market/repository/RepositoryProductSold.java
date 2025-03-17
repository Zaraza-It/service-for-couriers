package org.example.market.repository;

import org.example.market.entity.PurchasedAndSoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProductSold extends JpaRepository<PurchasedAndSoldProduct, Long> {
}
