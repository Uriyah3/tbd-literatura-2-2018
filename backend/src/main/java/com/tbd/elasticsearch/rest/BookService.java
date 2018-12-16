package com.tbd.elasticsearch.rest;

import java.util.ArrayList;
import java.util.List;
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
	
	
	
	
	
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public List<Book> topBook() {
		return  bookRepository.findAllByOrderByHitsDesc();
	}
	
	@RequestMapping(value = "/top10", method = RequestMethod.GET)
	public List<Book> top10Book() {
		return  bookRepository.findTop10ByOrderByHitsDesc();
	}
	
	@RequestMapping(value = "/top/genero/{id}", method = RequestMethod.GET)
	public List<Book> topBookGenre(@PathVariable("id") Long id) {
		return  bookRepository.findByGenreIdOrderByHitsDesc(id);
	}
	@RequestMapping(value = "/top10/genero/{id}", method = RequestMethod.GET)
	public List<Book> top10BookGenre(@PathVariable("id") Long id) {
		List<Book> topLibros= bookRepository.findByGenreIdOrderByHitsDesc(id);
		List<Book> nuevaLista=new ArrayList<Book>();
		
		Integer i=0;
		while (i<10) {
			nuevaLista.add(topLibros.get(i));
			i=i+1;
		}
		return nuevaLista;
	}
	
	@RequestMapping(value = "/top/autor/{id}", method = RequestMethod.GET)
	public List<Book> topBookAuthor(@PathVariable("id") Long id) {
		return  bookRepository.findByAuthorIdOrderByHitsDesc(id);
	}
	
	
	
	
	
	
}
