package com.redvinca.assignment.ecom_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redvinca.assignment.ecom_backend.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	//Finds a cart item by product ID.
	Optional<Cart> findByProductId(Long productId);
	//     * Calculates the total price of all items in the cart.
	@Query(value = "SELECT SUM(p.price * c.quantity) AS total_price FROM cart c JOIN product p ON c.product_id = p.id", nativeQuery = true)
	Double calculateTotalPrice();
	
}
