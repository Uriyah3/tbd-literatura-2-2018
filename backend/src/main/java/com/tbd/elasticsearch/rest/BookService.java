package com.tbd.elasticsearch.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.repository.BookRepository;

@CrossOrigin(maxAge=3600)
@RestController  
@RequestMapping("/book")
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Book> getAllFilms() {
		return bookRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Book> findOne(@PathVariable("id") Long id) {
		return bookRepository.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Book create(@RequestBody Book resource) {
	     return bookRepository.save(resource);
	}
}
