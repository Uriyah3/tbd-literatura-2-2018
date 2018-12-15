package com.tbd.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.entities.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>{
	

}