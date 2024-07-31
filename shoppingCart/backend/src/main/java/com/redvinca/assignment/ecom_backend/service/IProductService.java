package com.redvinca.assignment.ecom_backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.redvinca.assignment.ecom_backend.model.Product;

// Service interface for managing product operations.
public interface IProductService {

	//This method is responsible for adding a new product to the inventory.
	Product createProduct(Product product, MultipartFile file) throws IOException;

    //This method fetches all products available in the inventory.
    public List<Product> getAllProducts();

    //This method fetches a specific product from the inventory based on its ID.
    public Product getProductById(Long id);
}
