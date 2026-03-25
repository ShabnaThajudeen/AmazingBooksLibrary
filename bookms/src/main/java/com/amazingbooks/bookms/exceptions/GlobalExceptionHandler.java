package com.amazingbooks.bookms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IsbnAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> handleIsbnAlreadyExistException(IsbnAlreadyExistException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT );
	}
	
	@ExceptionHandler(IsbnNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleIsbnNotExistException(IsbnNotExistException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND );
	}
	
	@ExceptionHandler(BookIdNotExistException.class)	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleBookIdNotExistException(BookIdNotExistException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND );
	}
}
