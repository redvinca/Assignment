package com.redvinca.assignment.ecom_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redvinca.assignment.ecom_backend.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmail(String email);
}
