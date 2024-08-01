package com.redvinca.assignment.ecom_backend.service;

import java.util.List;

import com.redvinca.assignment.ecom_backend.model.Cart;
import com.redvinca.assignment.ecom_backend.request.AddToCartRequest;
import com.redvinca.assignment.ecom_backend.request.DeleteItemToCartRequest;
import com.redvinca.assignment.ecom_backend.request.UpdateQuantityRequest;
import com.redvinca.assignment.ecom_backend.response.AddToCartResponse;
import com.redvinca.assignment.ecom_backend.response.DeleteItemToCartResponse;
import com.redvinca.assignment.ecom_backend.response.MessageResponse;

//Service interface for managing shopping cart operations.
public interface ICartService {

	// Deletes an item from the cart.
	public DeleteItemToCartResponse deleteItemToCart(DeleteItemToCartRequest cartRequest);

	// Adds a product to the cart.
	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest);

	// Retrieves all items in the cart.
	public List<Cart> getAllCartItems();

	//Calculates the total price of all items in the cart.
	public double getTotalPrice();

	// Calculates the total quantity of all items in the cart.
	public int getTotalQuantity();

	// Updates the quantity of a cart item based on a quantity change request.
	public MessageResponse updateQuantityIncreaseDecrease(UpdateQuantityRequest request);

	void clearSelectedItems(List<Long> cartItemIds);
	
	
}
