package com.tbd.elasticsearch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.elasticsearch.entities.Genre;



public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>{
	

}
