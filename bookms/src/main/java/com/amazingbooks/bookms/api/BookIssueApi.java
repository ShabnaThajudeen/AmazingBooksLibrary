package com.amazingbooks.bookms.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazingbooks.bookms.domain.Book;
import com.amazingbooks.bookms.exceptions.BookIdNotExistException;
import com.amazingbooks.bookms.exceptions.IsbnNotExistException;
import com.amazingbooks.bookms.repo.BookRepo;

@RestController
public class BookIssueApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookIssueApi.class);
	
	@Autowired
	private BookRepo bookRepo;	
	
	@PutMapping("/books/updatecopy/{isbn}")
	public ResponseEntity<Book> updateBookCopy(@PathVariable("isbn") String isbn){
		LOGGER.info("Getting book details of isbn {} to update book copy", isbn);		
		Optional<Book> bookFound = bookRepo.findByIsbn(isbn);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with isbn {} not found", isbn);
			throw new IsbnNotExistException("Book with isbn " + isbn + " not exist!");
		}
		Book book = bookFound.get();
		book.setIssuedCopies(book.getIssuedCopies() + 1);
		bookRepo.save(book);
		LOGGER.info("Updated book {} copy.", isbn);
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/books/cancelissue/{bookId}")
	public ResponseEntity<Book> cancelBookIssue(@PathVariable("bookId") Integer id){
		LOGGER.info("Getting book details of id {}", id);		
		Optional<Book> bookFound = bookRepo.findById(id);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with id {} not found", id);
			throw new BookIdNotExistException("Book with id " + id + " not exist!");
			//return ResponseEntity.notFound().build();
		}
		Book book = bookFound.get();
		book.setIssuedCopies(book.getIssuedCopies() - 1);
		bookRepo.save(book);
		return ResponseEntity.ok(book);
	}
}
