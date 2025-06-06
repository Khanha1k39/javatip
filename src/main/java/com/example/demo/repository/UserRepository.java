package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String id);
    User getUserById(Long id);
    Optional<User> getUserByName(String name);
    User getUserByEmail(String email);
    Boolean existsById(long id);
}
