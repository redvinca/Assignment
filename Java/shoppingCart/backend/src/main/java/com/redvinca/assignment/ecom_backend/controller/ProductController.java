package com.redvinca.assignment.ecom_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redvinca.assignment.ecom_backend.constants.Constants;
import com.redvinca.assignment.ecom_backend.exception.ProductNotFoundException;
import com.redvinca.assignment.ecom_backend.model.Product;
import com.redvinca.assignment.ecom_backend.service.IProductService;

@RestController
@RequestMapping("${product.api.url}")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService iProductService;

    @PostMapping("${product.api.createProduct}")
    public ResponseEntity<?> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("file") MultipartFile file) {
        logger.info(Constants.CONTROLLER_CREATE_PRODUCT_STARTED, product.getName());
        try {
            Product createdProduct = iProductService.createProduct(product, file);
            logger.info(Constants.CONTROLLER_PRODUCT_CREATED_SUCCESSFULLY, createdProduct.getName());
            return ResponseEntity.ok(Constants.CONTROLLER_PRODUCT_CREATED_SUCCESSFULLY);
        } catch (Exception e) {
            logger.error(Constants.PRODUCT_DETAILS_INVALID, e);
            return ResponseEntity.status(500).body(Constants.PRODUCT_DETAILS_INVALID);
        }
    }

    @GetMapping("${product.api.getAllProducts}")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info(Constants.CONTROLLER_GET_ALL_PRODUCTS_STARTED);
        List<Product> products = iProductService.getAllProducts();
        logger.info(Constants.CONTROLLER_RETRIEVED_PRODUCTS, products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("${product.api.getProductById}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        logger.info(Constants.CONTROLLER_GET_PRODUCT_BY_ID_STARTED, id);
        try {
            Product product = iProductService.getProductById(id);
            logger.info(Constants.CONTROLLER_PRODUCT_FOUND, id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            logger.error(Constants.PRODUCT_NOT_FOUND, e);
            return ResponseEntity.status(404).body(Constants.PRODUCT_NOT_FOUND);
        }
    }
}
