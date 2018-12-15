package com.tbd.mongo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.mongo.entities.Genre;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>{
	

}
