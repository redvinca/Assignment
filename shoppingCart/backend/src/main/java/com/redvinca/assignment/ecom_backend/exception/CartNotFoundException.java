package com.redvinca.assignment.ecom_backend.exception;

/**
 * Custom exception class for cart not found scenario.
 */
public class CartNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950712599683028659L;

	public CartNotFoundException(String message) {
		super(message);
	}

}
