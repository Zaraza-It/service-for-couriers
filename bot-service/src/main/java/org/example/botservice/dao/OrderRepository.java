package org.example.botservice.dao;

import org.example.botservice.dto.Order;
import org.example.botservice.dto.ProductOnly;
import org.example.botservice.dto.TableProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  //  @Query(nativeQuery = true,value = "SELECT o.product FROM orders o")
    //    List<TableProduct> findAllProduct();
 @NativeQuery(value = "SELECT o.product FROM orders o")
 List<TableProduct> findAllProduct();

    //@Query("SELECT new org.example.botservice.dto.TableProduct(o.product) FROM Order o")
    //List<TableProduct> findAllProduct();

 //org.example.botservice.dto.TableProduct
}
