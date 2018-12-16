package com.tbd.elasticsearch.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;



public interface GenreRepository extends JpaRepository<Genre, Long>{
	
	public List <Genre> findAllByOrderByHitsDesc();
	
}
