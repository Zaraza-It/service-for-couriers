package org.example.serviceforcouriers.repository;

import org.aspectj.weaver.ast.Or;
import org.example.serviceforcouriers.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
