package com.redvinca.assignment.ecom_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.redvinca.assignment.ecom_backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
