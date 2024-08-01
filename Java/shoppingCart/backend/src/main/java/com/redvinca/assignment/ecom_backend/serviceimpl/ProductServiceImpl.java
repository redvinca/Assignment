package com.redvinca.assignment.ecom_backend.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.redvinca.assignment.ecom_backend.constants.Constants;
import com.redvinca.assignment.ecom_backend.exception.ProductNotFoundException;
import com.redvinca.assignment.ecom_backend.model.Image;
import com.redvinca.assignment.ecom_backend.model.Product;
import com.redvinca.assignment.ecom_backend.repository.ImageRepository;
import com.redvinca.assignment.ecom_backend.repository.ProductRepository;
import com.redvinca.assignment.ecom_backend.service.IProductService;
import com.redvinca.assignment.ecom_backend.util.ValidationUtil;

@Service
public class ProductServiceImpl implements IProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Value("${image.upload.dir}")
	private String uploadDir;

	@Override
	public Product createProduct(Product product, MultipartFile file) {
		logger.info(Constants.CREATE_PRODUCT_STARTED, product.getName());

		if (ValidationUtil.isNullOrEmpty(product.getName()) || ValidationUtil.isNullOrZero(product.getPrice())
				|| ValidationUtil.isNullOrNegative(product.getStock())) {
			throw new ProductNotFoundException(Constants.PRODUCT_DETAILS_INVALID);
		}

		Product createdProduct = productRepository.save(product);

		if (file != null && !file.isEmpty()) {
			try {
				// Ensure the upload directory exists
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				// Save the file
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Save the image details
				Image image = new Image();
				image.setImageUrl(fileName);
				image.setProduct(createdProduct);
				imageRepository.save(image);

			} catch (IOException e) {
				logger.error("Failed to save file", e);
				throw new RuntimeException("Failed to save file", e);
			}
		}

		logger.info(Constants.CREATE_PRODUCT_ENDED, product.getName());
		return createdProduct;
	}

	@Override
	public List<Product> getAllProducts() {
	    logger.info(Constants.GET_ALL_PRODUCTS_STARTED);

	    List<Product> products = productRepository.findAll();
	    products.forEach(product -> product.setImages(imageRepository.findByProduct(product)));
	    logger.info(Constants.GET_ALL_PRODUCTS_ENDED, products.size());
	    return products;
	}

	@Override
	public Product getProductById(Long id) {
	    logger.info(Constants.GET_PRODUCT_BY_ID_STARTED, id);

	    Product product = productRepository.findById(id).orElse(null);
	    if (product != null) {
	        product.setImages(imageRepository.findByProduct(product));
	        logger.info(Constants.PRODUCT_FOUND, id);
	    } else {
	        logger.error(Constants.PRODUCT_NOT_FOUND);
	        throw new ProductNotFoundException("Product not found with id: " + id);
	    }
	    logger.info(Constants.GET_PRODUCT_BY_ID_ENDED, id);
	    return product;
	}

}
