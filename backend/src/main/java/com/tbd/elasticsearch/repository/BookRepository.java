package com.tbd.elasticsearch.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.elasticsearch.entities.Book;



public interface BookRepository extends JpaRepository<Book, Long>{
	

	public List <Book> findByAuthorId(Long id); 
	

	
	
	public List <Book> findAllByOrderByHitsDesc();
	public List <Book> findTop10ByOrderByHitsDesc();

	public List <Book> findByGenreIdOrderByHitsDesc(Long genreId);
	//public List <Book> findByGenreTop10OrderByHitsDesc(Long genreId);
	
	public List <Book> findByAuthorIdOrderByHitsDesc(Long authorId);
	
	
	
	
}