package com.tbd.mongo.rest;


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

import com.tbd.mongo.entities.Genre;
import com.tbd.mongo.repository.GenreRepository;

@CrossOrigin(maxAge=3600)
@RestController  
@RequestMapping("/genre")
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Genre> getAllFilms() {
		System.out.println("PASA");
		return genreRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Genre> findOne(@PathVariable("id") Long id) {
		return genreRepository.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Genre create(@RequestBody Genre resource) {
	     return genreRepository.save(resource);
	}
}
