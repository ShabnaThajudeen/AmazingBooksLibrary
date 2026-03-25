package com.amazingbooks.bookms.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazingbooks.bookms.domain.Book;
import com.amazingbooks.bookms.exceptions.BookIdNotExistException;
import com.amazingbooks.bookms.exceptions.IsbnAlreadyExistException;
import com.amazingbooks.bookms.exceptions.IsbnNotExistException;
import com.amazingbooks.bookms.repo.BookRepo;

@RestController
public class BookResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
	
	@Autowired
	private BookRepo bookRepo;
	
	//Rest API to get all book details
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		LOGGER.info("Getting books details.");		
		return bookRepo.findAll();
	}
	
	//Rest API to get book details with isbn
	@GetMapping("/books/{isbn}")
	public ResponseEntity<Book> findBookByIsbn(@PathVariable("isbn") String isbn){
		LOGGER.info("Getting book details of isbn {}", isbn);		
		Optional<Book> bookFound = bookRepo.findByIsbn(isbn);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with isbn {} not found", isbn);
			//throw new IsbnNotExistException("Book with isbn " + isbn + " not exist!");
			return ResponseEntity.notFound().build();
		}
		LOGGER.info("Book details for isbn {}:", isbn);
		LOGGER.info(bookFound.get().toString());
		return ResponseEntity.ok(bookFound.get());
	}
	
	//Rest API to get book details with bookId
	@GetMapping("/books/find/{bookId}")
	public ResponseEntity<Book> findBookById(@PathVariable("bookId") Integer id){
		LOGGER.info("Getting book details of id {}", id);		
		Optional<Book> bookFound = bookRepo.findById(id);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with id {} not found", id);
			throw new BookIdNotExistException("Book with id " + id + " not exist!");
		}
		LOGGER.info("Book details for id {}:", id);
		LOGGER.info(bookFound.get().toString());
		return ResponseEntity.ok(bookFound.get());
	}
	
	//Rest API to add a new book
	@PostMapping("/books/add")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		LOGGER.info("Adding new book details.");
		
		//Checking if the isbn details already exist		
		Optional<Book> bookFound = bookRepo.findByIsbn(book.getIsbn());
		if(bookFound.isEmpty()) {
			LOGGER.info("Book with isbn {} doesn't exist", book.getIsbn());
			//book.setBookId(null);
			//LOGGER.info("Setting book id as null.");
			
			Book newBook = bookRepo.save(book);
					
			LOGGER.info("Saved book details");
			return ResponseEntity.created(URI.create("http://localhost:8081/books/" + newBook.getIsbn().toString())).body(newBook);
		}
		LOGGER.error("Book with isbn {} already exist.", book.getIsbn());
		throw new IsbnAlreadyExistException("Book with isbn " + book.getIsbn() + " exist. You can make updates if required.");
	}
	
	//Rest API to delete book details with isbn
	@DeleteMapping("/books/delete/{isbn}")
	public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn){
		LOGGER.info("Deleting book details.");
		
		//Checking if the isbn details exist		
		Optional<Book> bookFound = bookRepo.findByIsbn(isbn);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with isbn {} not found", isbn);
			throw new IsbnNotExistException("Book with isbn " + isbn + " not exist!");
		}
		
		bookRepo.deleteById(bookFound.get().getBookId());
		LOGGER.info("Deleted book details.");		
		return ResponseEntity.noContent().build();
	}
	
	//Rest API to update book details with isbn
	@PutMapping("/books/edit/{isbn}")
	public ResponseEntity<Book> updateBook(@PathVariable("isbn") String isbn, @RequestBody Book book){
		LOGGER.info("Checking if book with isbn exist.");
		
		Optional<Book> bookFound = bookRepo.findByIsbn(isbn);
		if(bookFound.isEmpty()) {
			LOGGER.error("Book with isbn {} not found", isbn);
			throw new IsbnNotExistException("Book with isbn " + isbn + " not exist!");
		}
		
		Book toUpdate = bookFound.get();
		toUpdate.setAuthor(book.getAuthor());
		toUpdate.setIssuedCopies(book.getIssuedCopies());
		toUpdate.setPublishedDate(book.getPublishedDate());		
		toUpdate.setTitle(book.getTitle());
		toUpdate.setTotalCopies(book.getTotalCopies());
		
		Book updatedBook = bookRepo.save(toUpdate);
		LOGGER.info("Updated book details.");
		
		return ResponseEntity.ok(updatedBook);
	}		
}
