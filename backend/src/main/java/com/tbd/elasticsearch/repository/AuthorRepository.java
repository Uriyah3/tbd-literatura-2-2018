package com.tbd.elasticsearch.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Book;



public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	public List<Author> findByGenreId(Long id);


	public Author findByName(String name);

	Author findAuthorById(Long id);
	
	public List <Author> findAllByOrderByHitsDesc();
	public List <Author> findByGenreIdOrderByHitsDesc(Long genreId);
	
	
}