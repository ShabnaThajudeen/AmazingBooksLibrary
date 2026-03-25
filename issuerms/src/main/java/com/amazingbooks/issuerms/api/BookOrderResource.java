package com.amazingbooks.issuerms.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.amazingbooks.issuerms.domain.BookDTO;
import com.amazingbooks.issuerms.domain.BookOrder;
import com.amazingbooks.issuerms.exceptions.BookCopyNotAvailableException;
import com.amazingbooks.issuerms.exceptions.OrderNotExistException;
import com.amazingbooks.issuerms.repo.BookOrderRepo;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@RestController
public class BookOrderResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderResource.class);
	
	@Autowired
	private BookOrderRepo orderRepo;
	
	@Autowired
	private WebClient webClient;	
	
	//REST API to get all book issue details
	@GetMapping("/bookorder")
	public List<BookOrder> getAllOrders(){
		LOGGER.info("Getting order details.");	
		
		return orderRepo.findAll();
	}
	
	//REST API to get all book issue details with orderId
	@GetMapping("/bookorder/{orderId}")
	public ResponseEntity<BookOrder> findOrderById(@PathVariable("orderId") Integer id){
		LOGGER.info("Getting order details.");	
		
		Optional<BookOrder> orderFound = orderRepo.findById(id);
		if(orderFound.isEmpty()) {
			LOGGER.error("Details for order {} not found", id);
			throw new OrderNotExistException("Order with id " + id + " not exist!");
		}
		
		LOGGER.info("Order details for id {}:", id);
		LOGGER.info(orderFound.get().toString());
		return ResponseEntity.ok(orderFound.get());
	}
	
	//REST API to issue a book to customer
	@SuppressWarnings("rawtypes")
	@PostMapping("/bookorder/place")
	@CircuitBreaker(name = "bookmsclient", fallbackMethod = "bookIssueFallBack")
	public Mono bookIssue(@RequestBody BookOrder order){
		LOGGER.info("About to issue book to customer");
		
		//Getting book details from bookms
		Mono<BookDTO> bookMono = webClient
				.get()
				.uri("/books/" + order.getIsbn())
				.retrieve()
				.bodyToMono(BookDTO.class);		
		
		LOGGER.info("Fetched book details from bookms");	
		
			
		Mono<Object> mono = bookMono.map(book -> {			
			LOGGER.info("Book Details is: ", book.toString());
			int availableCopies = book.getTotalCopies() - book.getIssuedCopies();
			LOGGER.info("Available copies = ", availableCopies);
			
			if(availableCopies == 0) {				
				LOGGER.error("Book copy not available to issue");
				//return Mono.just("Book copy not available to issue");
				throw new BookCopyNotAvailableException("Book copy not available to issue");
			}		
			
			LOGGER.info("Book copy available to issue");			
			BookOrder bookOrder = new BookOrder();
			bookOrder.setOrderId(null);
			bookOrder.setBookId(book.getBookId());
			bookOrder.setCustId(order.getCustId());
			bookOrder.setIsbn(book.getIsbn());
			bookOrder.setNoOfCopies(1);
			bookOrder.setStatus("Issued");
			
			orderRepo.save(bookOrder);
			
			LOGGER.info("Book with bookId {}, isbn {} issued to customer {}", bookOrder.getBookId(), bookOrder.getIsbn(), bookOrder.getCustId());
			
			LOGGER.info("Updating copy in bookms");
			return webClient
			.put()			
			.uri("books/updatecopy/" + order.getIsbn())
			.retrieve()
			.bodyToMono(Object.class);
		});	
		
		return mono;		
	}
	
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private Mono bookIssueFallBack(CallNotPermittedException ex) {
		LOGGER.error("Fallback is invoked");		 
		return Mono.just(new String("Network traffic high. Try after some time..."));
	}
	
	
	//REST API to cancel book issue to a customer
	@SuppressWarnings("rawtypes")
	@PutMapping("/bookorder/cancel/{id}")
	@CircuitBreaker(name = "bookmsclient", fallbackMethod = "cancelIssueFallBack")
	public Mono cancelBookIssue(@PathVariable("id") Integer id){
		LOGGER.info("Getting order details.");		
		Optional<BookOrder> orderFound = orderRepo.findById(id);
		if(orderFound.isEmpty()) {
			LOGGER.error("Details for order {} not found", id);
			throw new OrderNotExistException("Order with id " + id + " not exist!");
		}
		BookOrder order = orderFound.get();
		order.setStatus("Returned");
		orderRepo.save(order);
		
		return webClient
		.put()
		.uri("books/cancelissue/" + order.getBookId())
		.retrieve()
		.bodyToMono(Object.class);			
	}
	
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private Mono cancelIssueFallBack(CallNotPermittedException ex) {
		return Mono.just("Book issue cancellation not successful. Try after some time.");
	}
	
}
