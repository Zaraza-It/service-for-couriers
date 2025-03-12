package org.example.botservice.dao;

import jakarta.persistence.Table;
import org.example.botservice.dto.BackDto;
import org.example.botservice.dto.Order;
import org.example.botservice.dto.TableProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  //  @Query(nativeQuery = true,value = "SELECT o.product FROM orders o")
    //    List<TableProduct> findAllProduct();
 //@NativeQuery(value = "select product from orders")
 //List<TableProduct> findAllProduct();
    boolean findCallbackByOrderId(Long orderId);
    Order findOrderByCallback(UUID callback);
 //BackDto findProductByCallback(String callback);

//Order saveByOrder(Order order);
 //org.example.botservice.dto.TableProduct
}
