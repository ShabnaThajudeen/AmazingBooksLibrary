package com.amazingbooks.bookms.exceptions;

public class IsbnNotExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public IsbnNotExistException() {
		super();		
	}

	public IsbnNotExistException(String message) {
		super(message);		
	}
}
