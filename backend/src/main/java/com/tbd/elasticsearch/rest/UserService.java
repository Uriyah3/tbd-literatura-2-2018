package com.tbd.elasticsearch.rest;

import java.util.ArrayList;
import java.util.List;

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.repository.AuthorRepository;
import com.tbd.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.tbd.elasticsearch.entities.User;

import java.util.Optional;

@CrossOrigin(maxAge=3600)
@RestController
@RequestMapping("/user")
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> findOne(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User create(@RequestBody User resource) {
        return userRepository.save(resource);
    }


    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public List<User> findTop() {
        return userRepository.findAllByOrderByScoreDesc();
    }


    @RequestMapping(value = "/top10", method = RequestMethod.GET)
    public List<User> findTop10() {
        return userRepository.findTop10ByOrderByScoreDesc();
    }





}
