package org.example.market.repository;

import org.example.market.dto.ProductDTO;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

List<Product>findAllByCategoryProduct(String categoryProduct);


List<Product> findAllByUser(User user);


@NativeQuery("SELECT p.product_name,p.username,p.image_data FROM products p")
List<ProductDTO> findAllProducts();

Optional<Product> findProductByProductId(Long id);

@NativeQuery("SELECT COUNT(*) > 0 AS exists FROM products WHERE product_id = ?")
boolean findProductByIdAndUsername(Long id, String username);


@NativeQuery("SELECT * FROM products WHERE username = :username AND product_id = :productId")
Optional<Product> findProductByUsernameAndProductId(@Param("username") String username,@Param("productI   d") Long productId);

@NativeQuery("SELECT count(*)")
Optional<Integer> findAllSalesAndPurchases();

//SELECT COUNT(*) > 0 AS exists
//FROM products
//WHERE product_id = 123 AND username = 'exampleUser ';

//SELECT *
//FROM products
//WHERE product_id = ? AND username = ?;
}
