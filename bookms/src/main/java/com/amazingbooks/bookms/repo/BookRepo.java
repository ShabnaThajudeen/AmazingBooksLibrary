package com.amazingbooks.bookms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazingbooks.bookms.domain.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	Optional<Book> findByIsbn(String isbn);

	void deleteByIsbn(String isbn);
}
