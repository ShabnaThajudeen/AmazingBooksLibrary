package com.amazingbooks.bookms.exceptions;

public class BookIdNotExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BookIdNotExistException() {
		super();		
	}

	public BookIdNotExistException(String message) {
		super(message);		
	}
}
