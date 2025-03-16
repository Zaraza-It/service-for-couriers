package org.example.serviceforcouriers.repository;

import org.example.serviceforcouriers.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o.product from Order o")
    List<Order> findAllProduct();

}
