package com.amazingbooks.issuerms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_order")
public class BookOrder {
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	
	private String isbn;
	private Integer bookId;
	private Integer custId;
	private Integer noOfCopies;
	private String status;
	
	public BookOrder() {
		super();		
	}

	public BookOrder(Integer orderId, String isbn, Integer bookId, Integer custId, Integer noOfCopies, String status) {
		super();
		this.orderId = orderId;
		this.isbn = isbn;
		this.bookId = bookId;
		this.custId = custId;
		this.noOfCopies = noOfCopies;
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
