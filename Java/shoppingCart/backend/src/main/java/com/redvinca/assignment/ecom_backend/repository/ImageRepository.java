package com.redvinca.assignment.ecom_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.redvinca.assignment.ecom_backend.model.Image;
import com.redvinca.assignment.ecom_backend.model.Product;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct(Product product);
}
