package com.amazingbooks.issuerms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OrderNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleOrderNotExistException(OrderNotExistException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND );
	}	
	
	@ExceptionHandler(BookNotAvailableException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.EXPECTATION_FAILED );
	}	
	
	
	@ExceptionHandler(BookCopyNotAvailableException.class)	
	@ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
	public ResponseEntity<String> handleBookCopyNotAvailableException(BookCopyNotAvailableException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
	}	
}
