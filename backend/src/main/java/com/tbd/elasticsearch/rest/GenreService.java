package com.tbd.elasticsearch.rest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.tbd.elasticsearch.entities.Author;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.repository.GenreRepository;

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
	
	/*
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public List<Genre> topGenre() {
		
		return  genreRepository.findAllByOrderByHitsDesc();
	}
	*/
	
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public Map<String, ArrayList> topGenre() {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
        List<Genre> lista= genreRepository.findAllByOrderByHitsDesc();
        for (Genre genre : lista) {
        	data.add(genre.getHits());
        	label.add(genre.getName());
		}
        
        result.put("data", data);
        result.put("labels", label);
		return  result;
	}

	@RequestMapping(value = "/one/genre/{name}", method = RequestMethod.GET)
	public Genre genreByName(@PathVariable("name") String name) {

		Genre genero=  genreRepository.findByName(name);
		return genero;
	}
	
	
}
