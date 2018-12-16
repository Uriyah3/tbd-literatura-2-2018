package com.tbd.elasticsearch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tbd.elasticsearch.entities.Author;



public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>{
	

}