package org.example.market.repository;

import org.example.market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {

List<Product>findAllByCategoryProduct(String categoryProduct);


}
