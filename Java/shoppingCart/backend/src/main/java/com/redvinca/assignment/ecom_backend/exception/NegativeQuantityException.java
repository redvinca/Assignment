package com.redvinca.assignment.ecom_backend.exception;

/**
 * Custom exception class for negative quantity scenario.
 */
public class NegativeQuantityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011941422737314068L;

	public NegativeQuantityException(String message) {
		super(message);
	}
}
