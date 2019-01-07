package com.tbd.elasticsearch.repository;




import java.util.List;
import java.util.Optional;

import com.tbd.elasticsearch.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

import com.tbd.elasticsearch.entities.User;



public interface UserRepository extends JpaRepository<User, Long>{


    public List <User> findAllByOrderByScoreDesc();
    public List <User> findTop10ByOrderByScoreDesc();


}
