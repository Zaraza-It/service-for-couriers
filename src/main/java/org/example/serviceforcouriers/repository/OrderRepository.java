package org.example.serviceforcouriers.repository;

import org.example.serviceforcouriers.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
