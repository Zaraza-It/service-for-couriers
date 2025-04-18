package org.example.market.repository;

import org.example.market.dto.RoleDTO;
import org.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findByUsername(String username);

@NativeQuery("SELECT r.role\n" +
        "FROM users u\n" +
        "JOIN roles r ON u.user_id = r.user_id\n" +
        "WHERE u.username = :username")
Optional<RoleDTO> findRolesByUsername(@Param("username") String username);


}
