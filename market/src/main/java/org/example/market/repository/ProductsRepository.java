package org.example.market.repository;

import org.example.market.dto.ProductResponseDTO;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long> {

List<Product>findAllByCategoryProduct(String categoryProduct);


@NativeQuery(value = "SELECT p.product_name,p.category_product,p.quantity,p.product_price FROM products")
Optional<ProductResponseDTO> findAllProducts();

List<Product> findAllByUser(User user);
}
