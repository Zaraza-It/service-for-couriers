package org.example.botservice.dao;

import jakarta.persistence.EntityManager;
import org.example.botservice.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.product FROM Order o")
        Optional<Order> findAllProduct();
}
