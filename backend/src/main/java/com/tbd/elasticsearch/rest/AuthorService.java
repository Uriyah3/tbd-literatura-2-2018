package com.tbd.elasticsearch.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.repository.AuthorRepository;

@CrossOrigin(maxAge=3600)
@RestController  
@RequestMapping("/author")
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Author> getAllFilms() {
		return authorRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Author> findOne(@PathVariable("id") Long id) {
		return authorRepository.findById(id);
	}

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Author findOneByName(@PathVariable("name") String name) {
		return authorRepository.findByName(name);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Author create(@RequestBody Author resource) {
	     return authorRepository.save(resource);
	}
	
	@RequestMapping(value = "genre/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Author> findAuthorFromGenre(@PathVariable("id") Long id) {
		return authorRepository.findByGenreId(id);
	}
	
	
	
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public Map<String, ArrayList> topAuthor() {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
		List<Author> lista= authorRepository.findAllByOrderByHitsDesc();
		for (Author e : lista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
		result.put("data", data);
        result.put("labels", label);
		return  result;
	}
	
	@RequestMapping(value = "/top/genero/{id}", method = RequestMethod.GET)
	public Map<String, ArrayList> topBookGenre(@PathVariable("id") Long id) {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
        List<Author> lista=authorRepository.findByGenreIdOrderByHitsDesc(id);
        for (Author e : lista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
		result.put("data", data);
        result.put("labels", label);
		return  result;
	}
	
	
	
	
}
