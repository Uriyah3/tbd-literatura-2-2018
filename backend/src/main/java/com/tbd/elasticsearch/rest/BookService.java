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

import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.repository.AuthorRepository;
import com.tbd.elasticsearch.repository.BookRepository;

@CrossOrigin(maxAge=3600)
@RestController  
@RequestMapping("/book")
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

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
	
	//Para obtener los libros de cada autor
	@RequestMapping(value = "author/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Book> bookFromAuthor(@PathVariable("id") Long id) {
		return  bookRepository.findByAuthorId(id);
	}
	
	
	
	
	//Funciones para el front  
	
	
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public Map<String, ArrayList> topBook() {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
        List<Book> lista= bookRepository.findAllByOrderByHitsDesc();
        for (Book e : lista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
		 result.put("data", data);
	     result.put("labels", label);
	     return  result;
	}
	
	@RequestMapping(value = "/top10", method = RequestMethod.GET)
	public Map<String, ArrayList> top10Book() {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
        List<Book> lista=  bookRepository.findTop10ByOrderByHitsDesc();
        for (Book e : lista) {
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
        
        List<Book> lista=  bookRepository.findByGenreIdOrderByHitsDesc(id);
        for (Book e : lista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
		 result.put("data", data);
	        result.put("labels", label);
			return  result;
	}
	@RequestMapping(value = "/top10/genero/{id}", method = RequestMethod.GET)
	public Map<String, ArrayList> top10BookGenre(@PathVariable("id") Long id) {
		List<Book> topLibros= bookRepository.findByGenreIdOrderByHitsDesc(id);
		List<Book> nuevaLista=new ArrayList<Book>();
		
		Integer i=0;
		while (i<10) {
			nuevaLista.add(topLibros.get(i));
			i=i+1;
		}
		
		//Trabajar con nuevaLista
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        for (Book e : nuevaLista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
        result.put("data", data);
        result.put("labels", label);
		return  result;
	}
	
	@RequestMapping(value = "/top/autor/{id}", method = RequestMethod.GET)
	public Map<String, ArrayList>topBookAuthor(@PathVariable("id") Long id) {
		HashMap<String, ArrayList> result = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<Integer>();
        ArrayList<String> label = new ArrayList<String>();
        
        List<Book> lista=  bookRepository.findByAuthorIdOrderByHitsDesc(id);
        for (Book e : lista) {
        	data.add(e.getHits());
        	label.add(e.getName());
		}
		
		 result.put("data", data);
	        result.put("labels", label);
			return  result;
	}
	
	
	
	
	
	
}
