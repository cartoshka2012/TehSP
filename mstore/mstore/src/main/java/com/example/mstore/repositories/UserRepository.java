package com.example.mstore.repositories;

import com.example.mstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    abstract User findByEmail(String email);
}
