package com.amazingbooks.issuerms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazingbooks.issuerms.domain.BookDTO;
import com.amazingbooks.issuerms.domain.BookOrder;
import com.amazingbooks.issuerms.exceptions.BookCopyNotAvailableException;
import com.amazingbooks.issuerms.repo.BookOrderRepo;

import reactor.core.publisher.Mono;

@Service
public class BookIssueService {
	
	@Autowired
	private BookOrderRepo orderRepo;
	
	private int availableCopies;
	private Integer bookId;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookIssueService.class);
	
	public Integer checkAvailability(Mono<Object> objectMono) {
		Mono<BookDTO> bookMono = objectMono.map(obj -> (BookDTO) obj);
		//Mono<BookDTO> bookMono = objectMono.cast(BookDTO.class);
		bookMono.subscribe(b -> System.out.println(b.toString()));
		LOGGER.info("Book details: {}", bookMono.toString());
		
		bookMono.subscribe(book -> {
			availableCopies = book.getTotalCopies() - book.getIssuedCopies();
			LOGGER.info("Available copies = ", availableCopies);
			bookId = book.getBookId();
			LOGGER.info("BookId = ", bookId);
		});
		
		if(availableCopies == 0) {
			LOGGER.info("Available copies2 = ", availableCopies);
			LOGGER.info("BookId2 = ", bookId);
			LOGGER.error("Book copy not available to issue");
			throw new BookCopyNotAvailableException("Book copy not available to issue");
		}
		LOGGER.info("Book copy available to issue");
		return bookId;		
	}
	
	public BookOrder issueBook(String isbn, Integer custId, Integer bookId) {
		
		BookOrder bookOrder = new BookOrder();
		bookOrder.setOrderId(null);
		bookOrder.setBookId(bookId);
		bookOrder.setCustId(custId);
		bookOrder.setIsbn(isbn);
		bookOrder.setNoOfCopies(1);
		bookOrder.setStatus("Issued");
		
		orderRepo.save(bookOrder);
		
		LOGGER.info("Book with bookId {}, isbn {} issued to customer {}", bookId, isbn, custId);
		return bookOrder;
	}
}
