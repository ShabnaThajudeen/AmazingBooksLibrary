package com.amazingbooks.issuerms.exceptions;

public class OrderNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotExistException() {
		super();		
	}

	public OrderNotExistException(String message) {
		super(message);		
	}
}
