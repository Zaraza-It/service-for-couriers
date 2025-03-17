package org.example.market.repository;

import org.example.market.dto.ProductDTO;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long> {

List<Product>findAllByCategoryProduct(String categoryProduct);


List<Product> findAllByUser(User user);


@NativeQuery("SELECT p.product_name,p.username FROM products p")
List<ProductDTO> findAllProducts();


}
