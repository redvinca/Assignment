package com.redvinca.assignment.ecom_backend.exception;

public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1385062184238907545L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}
