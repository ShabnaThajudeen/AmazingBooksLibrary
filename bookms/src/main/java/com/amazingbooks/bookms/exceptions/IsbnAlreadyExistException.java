package com.amazingbooks.bookms.exceptions;

public class IsbnAlreadyExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public IsbnAlreadyExistException() {
		super();		
	}

	public IsbnAlreadyExistException(String message) {
		super(message);		
	}
}
