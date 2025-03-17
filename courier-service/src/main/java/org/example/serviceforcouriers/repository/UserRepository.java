package org.example.serviceforcouriers.repository;

import org.example.serviceforcouriers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
