package com.amazingbooks.issuerms.exceptions;

public class BookCopyNotAvailableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookCopyNotAvailableException() {
		super();		
	}

	public BookCopyNotAvailableException(String message) {
		super(message);		
	}
}
