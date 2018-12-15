package com.tbd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.entities.Genre;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>{
	

}
