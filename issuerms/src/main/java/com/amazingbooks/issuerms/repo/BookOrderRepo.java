package com.amazingbooks.issuerms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazingbooks.issuerms.domain.BookOrder;

@Repository
public interface BookOrderRepo extends JpaRepository<BookOrder, Integer>{

}
