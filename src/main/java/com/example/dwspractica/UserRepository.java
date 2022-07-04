package com.example.dwspractica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNombre(String name);
    boolean existsByNombre(String name);
}
