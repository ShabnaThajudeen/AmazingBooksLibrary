package com.amazingbooks.bookms.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
	@Id	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOKID")
	private Integer bookId;
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "title")
	private String title;
	@Column(name = "PUBLISHEDDATE")
	private LocalDate publishedDate;
	@Column(name = "TOTALCOPIES")
	private Integer totalCopies;
	@Column(name = "ISSUEDCOPIES")
	private Integer issuedCopies;
	@Column(name = "author")
	private String author;
	
	public Book() {
		super();		
	}

	public Book(Integer bookId, String isbn, String title, LocalDate publishedDate, Integer totalCopies,
			Integer issuedCopies, String author) {
		super();
		this.bookId = bookId;
		this.isbn = isbn;
		this.title = title;
		this.publishedDate = publishedDate;
		this.totalCopies = totalCopies;
		this.issuedCopies = issuedCopies;
		this.author = author;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Integer getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Integer getIssuedCopies() {
		return issuedCopies;
	}

	public void setIssuedCopies(Integer issuedCopies) {
		this.issuedCopies = issuedCopies;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
