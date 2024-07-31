package com.redvinca.assignment.ecom_backend.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redvinca.assignment.ecom_backend.constants.Constants;
import com.redvinca.assignment.ecom_backend.exception.InsufficientStockException;
import com.redvinca.assignment.ecom_backend.exception.NegativeQuantityException;
import com.redvinca.assignment.ecom_backend.exception.ProductNotFoundException;
import com.redvinca.assignment.ecom_backend.model.Cart;
import com.redvinca.assignment.ecom_backend.model.Product;
import com.redvinca.assignment.ecom_backend.repository.CartRepository;
import com.redvinca.assignment.ecom_backend.repository.ProductRepository;
import com.redvinca.assignment.ecom_backend.request.AddToCartRequest;
import com.redvinca.assignment.ecom_backend.request.DeleteItemToCartRequest;
import com.redvinca.assignment.ecom_backend.request.UpdateQuantityRequest;
import com.redvinca.assignment.ecom_backend.response.AddToCartResponse;
import com.redvinca.assignment.ecom_backend.response.DeleteItemToCartResponse;
import com.redvinca.assignment.ecom_backend.response.MessageResponse;
import com.redvinca.assignment.ecom_backend.service.ICartService;
import com.redvinca.assignment.ecom_backend.util.ValidationUtil;

@Service
public class CartServiceImpl implements ICartService {

	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Adds a product to the cart.
	 *
	 * @param productId the ID of the product to add to the cart.
	 * @return the updated cart item.
	 */

	@Override
	public AddToCartResponse addToCart(AddToCartRequest cartAddToCartRequest) {
		logger.info(Constants.ADD_TO_CART_STARTED, cartAddToCartRequest.getProductId());

		Long productId = cartAddToCartRequest.getProductId();
		if (ValidationUtil.isNullOrNegative(productId)) {
			throw new ProductNotFoundException(Constants.PRODUCT_ID_INVALID);
		}
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND));
		if (product.getStock() <= 0) {
			logger.error(Constants.PRODUCT_OUT_OF_STOCK, productId);
			throw new ProductNotFoundException(Constants.PORDUCT_OUT_OF_STOCK_EXCEPTION);
		}

		Optional<Cart> optionalCart = cartRepository.findByProductId(productId);
		Cart cart;
		if (optionalCart.isPresent()) {
			cart = optionalCart.get();
			if (cart.getQuantity() < product.getStock()) {
				cart.setQuantity(cart.getQuantity() + 1);
			} else {
				logger.error(Constants.QUANTITY_EXCEEDS_STOCK, productId);
				throw new InsufficientStockException(Constants.QUANTITY_EXCEEDS_STOCK);
			}
		} else {
			cart = new Cart();
			cart.setProduct(product);
			cart.setQuantity(1); // Default quantity
		}

		cartRepository.save(cart); // Save the cart item to persist changes

		AddToCartResponse addToCartResponse = new AddToCartResponse();
		addToCartResponse.setMessage(Constants.CART_ITEM_ADDED_SUCCESSFULLY); // Update with relevant message
		logger.info(Constants.ADD_TO_CART_ENDED, productId);
		return addToCartResponse;
	}

	/**
	 * Retrieves all items in the cart.
	 *
	 * @return a list of all cart items.
	 */
	@Override
	public List<Cart> getAllCartItems() {
		logger.info(Constants.GET_ALL_CART_ITEMS_STARTED);
		List<Cart> cartItems = cartRepository.findAll();
		logger.info(Constants.GET_ALL_CART_ITEMS_ENDED, cartItems.size());
		return cartItems;
	}

	/**
	 * Deletes an item from the cart.
	 *
	 * @param cartRequest the request containing the cart item to be deleted.
	 * @return a response indicating the result of the deletion.
	 */
	@Override
	public DeleteItemToCartResponse deleteItemToCart(DeleteItemToCartRequest cartRequest) {
		logger.info(Constants.DELETE_ITEM_TO_CART_STARTED, cartRequest.getCartId());

		Long cartId = cartRequest.getCartId();
		if (ValidationUtil.isNullOrNegative(cartId)) {
			logger.error(Constants.QUANTITY_LESS_THAN_ZERO, cartRequest.getCartId());
			throw new NegativeQuantityException(Constants.CART_ID_INVALID);
		}

		if (!cartRepository.existsById(cartId)) {
			logger.error(Constants.CART_ID_NOT_FOUND, cartId);
			throw new ProductNotFoundException(Constants.CART_ID_NOT_FOUND);
		}
		cartRepository.deleteById(cartId);

		DeleteItemToCartResponse cartResponse = new DeleteItemToCartResponse();
		cartResponse.setMessage(Constants.CART_ITEM_DELETED_SUCCESSFULLY);

		logger.info(Constants.DELETE_ITEM_TO_CART_ENDED, cartId);
		return cartResponse;
	}

	/**
	 * Calculates the total quantity of all items in the cart.
	 *
	 * @return the total quantity of all items in the cart.
	 */
	@Override
	public int getTotalQuantity() {
		logger.info(Constants.GET_TOTAL_QUANTITY_STARTED);
		List<Cart> cartItems = cartRepository.findAll();
		int totalQuantity = 0;
		for (Cart cart : cartItems) {
			totalQuantity += cart.getQuantity();
		}
		logger.info(Constants.GET_TOTAL_QUANTITY_ENDED, totalQuantity);
		return totalQuantity;
	}

	/**
	 * Calculates the total price of all items in the cart.
	 *
	 * @return the total price of all items in the cart.
	 */
	@Override
	public double getTotalPrice() {
		logger.info(Constants.GET_TOTAL_PRICE_STARTED);
		Double totalPrice = cartRepository.calculateTotalPrice();
		if(totalPrice==null) {
			totalPrice = Double.valueOf(0);
		}
		logger.info(Constants.GET_TOTAL_PRICE_ENDED, totalPrice);
		return totalPrice;
	}

	/**
	 * Updates the quantity of a cart item based on a quantity change request.
	 *
	 * @param request the request containing the cart item ID and the quantity
	 *                change.
	 * @return a response indicating the result of the quantity update.
	 */
	@Override
	public MessageResponse updateQuantityIncreaseDecrease(UpdateQuantityRequest request) {
		logger.info(Constants.UPDATE_QUANTITY_STARTED, request.getCartItemId());

		// Find the cart item by ID
		Optional<Cart> cartItem = cartRepository.findById(request.getCartItemId());

		if (!cartItem.isPresent()) {
			logger.error(Constants.CART_ID_NOT_FOUND, request.getCartItemId());
			throw new ProductNotFoundException(Constants.CART_ID_NOT_FOUND);
		}

		Cart cart = cartItem.get();
		Product product = cart.getProduct();

		// Update the quantity
		int newQuantity = cart.getQuantity() + request.getQuantityChange();
		if (newQuantity <= 0) {
			logger.error(Constants.QUANTITY_LESS_THAN_ZERO, request.getCartItemId());
			throw new ProductNotFoundException(Constants.QUANTITY_LESS_THAN_ZERO);
		}

		if (newQuantity > product.getStock()) {
			logger.error(Constants.QUANTITY_EXCEEDS_STOCK, product.getId());
			throw new InsufficientStockException(Constants.QUANTITY_EXCEEDS_STOCK);
		}
		cart.setQuantity(newQuantity);

		// Save the updated cart item
		cart = cartRepository.save(cart);

		MessageResponse response = new MessageResponse();
		response.setMessage(Constants.QUANTITY_UPDATED_SUCCESSFULLY);

		logger.info(Constants.UPDATE_QUANTITY_ENDED, request.getCartItemId());
		return response;
	}
	
	@Override
	public void clearSelectedItems(List<Long> cartItemIds) {
	    for (Long cartItemId : cartItemIds) {
	        cartRepository.deleteById(cartItemId);
	    }
	}

}
