package com.example.myFirstApp.repository;

import com.example.myFirstApp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailExcludingId(@Param("email") String email, @Param("id") String id);
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}