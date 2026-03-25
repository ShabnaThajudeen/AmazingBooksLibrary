package com.amazingbooks.issuerms.domain;

import java.time.LocalDate;

public class BookDTO {
	
    private Integer bookId;	
	private String isbn;	
	private String title;
	private LocalDate publishedDate;
	private Integer totalCopies;
	private Integer issuedCopies;
	private String author;	
	
	public BookDTO() {
		super();		
	}

	public BookDTO(Integer bookId, String isbn, String title, LocalDate publishedDate, Integer totalCopies,
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

	@Override
	public String toString() {
		return "BookDTO [bookId=" + bookId + ", isbn=" + isbn + ", title=" + title + ", publishedDate=" + publishedDate
				+ ", totalCopies=" + totalCopies + ", issuedCopies=" + issuedCopies + ", author=" + author + "]";
	}	
}
