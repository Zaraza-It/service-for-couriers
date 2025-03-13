package org.example.authservice.dao;

import org.example.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
User findByUsername(String username);
User findByEmail(String email);

Optional<User> findEmailByUsername(String username);
}
