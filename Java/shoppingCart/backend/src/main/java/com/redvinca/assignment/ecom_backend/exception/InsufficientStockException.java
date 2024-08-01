package com.redvinca.assignment.ecom_backend.exception;

/**
 * Custom exception class for insufficient stock scenario.
 */

public class InsufficientStockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8614235545455087152L;

	public InsufficientStockException(String message) {
		super(message);
	}
}
