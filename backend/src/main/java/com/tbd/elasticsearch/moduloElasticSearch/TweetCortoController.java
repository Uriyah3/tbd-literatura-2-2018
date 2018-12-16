package com.tbd.elasticsearch.moduloElasticSearch;

import org.elasticsearch.search.profile.ProfileShardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.tbd.elasticsearch.entities.Author;
import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.entities.Genre;
import com.tbd.elasticsearch.moduloMongo.Tweet;
import com.tbd.elasticsearch.moduloMongo.TweetService;
import com.tbd.elasticsearch.rest.AuthorService;
import com.tbd.elasticsearch.rest.BookService;
import com.tbd.elasticsearch.rest.GenreService;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ComponentScan
@RestController
@EnableAutoConfiguration
@RequestMapping("/elasticsearch")
public class TweetCortoController {
	
	
    @Autowired
    private TweetCortoDao tweetCortoDao;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private GenreService genreService;
    
    @Autowired
    private TweetService tweetService;
    


    public TweetCortoController(TweetCortoDao tweetCortoDao) {
        this.tweetCortoDao = tweetCortoDao;
    }

    
    
    //Insertar datos de mongo en elastic
    @GetMapping("/insert")
	public void armar(){
		List<Tweet> listaTweets=tweetService.getTweets();
		for (Tweet tweet : listaTweets) {
			TweetCorto tweetCorto=new TweetCorto();
			tweetCorto.setText(tweet.getText());
			Long idTweet=tweet.getId();
			String idStr= String.valueOf(idTweet);
			tweetCorto.setId(idStr);
			tweetCortoDao.insertTweetCorto(tweetCorto);
		}
	  
	}
    
    //Obtener datos de mongo por libro
    @GetMapping("/hits")
	public Integer cantidadHits(Book book){
    	
    	return tweetCortoDao.getCantidadHits(book);
	  
	}
    
    
    //Actualizar la lista de todos los libros 
    @GetMapping("/updateBook")
    public void updateBook() {
    	List <Book> listaLibros=new ArrayList<Book>();
    	listaLibros=(List<Book>) bookService.getAllFilms();
    	
    	for (Book book : listaLibros) {
    		Integer hits=cantidadHits(book);
    		System.out.printf("Cantida de Hits: %d\n" , hits );
  		  	System.out.println("En libro: "+book.getName());
  		  	//
  		  	Book bookUpdated=new Book();
  		  	//Se setan los atributos
  		  	bookUpdated.setId(book.getId());
  		  	bookUpdated.setName(book.getName());
  		  	bookUpdated.setAuthorId(book.getAuthorId());
  		  	bookUpdated.setGenreId(book.getGenreId());
  		  	bookUpdated.setHits(hits);
  		  	//Se actualiza
  		  	bookService.create(bookUpdated);
  		  
    		
		}
    }
    
    
  //Actualizar un Author
    @GetMapping("/hitsAuthor")
    public Integer hitsAuthor(Author author) {
    	
    	List <Book> listaLibros=new ArrayList<Book>();
    	listaLibros=bookService.bookFromAuthor(author.getId());
    	
    	Integer counter=0;
    	for (Book book : listaLibros) {
    		counter=counter+book.getHits();
		}

		//Se actualiza el author
		Author authorUpdated=new Author();
		//Se setan los atributos
		authorUpdated.setId(author.getId());
		authorUpdated.setName(author.getName());
		authorUpdated.setGenreId(author.getGenreId());
		authorUpdated.setHits(counter);
		//Se actualiza
		authorService.create(authorUpdated);
		return counter;
    }
    
    //Actualizar todos los autores 
    @GetMapping("/updateAuthor")
    public void updateAuthor() {
    	
    	//Sacar todos los autores
    	List <Author> listaAutores=new ArrayList<Author>();
    	listaAutores=(List<Author>) authorService.getAllFilms();
    	
    	for (Author author : listaAutores) {
    		//Se actualizan todos los autores
    		hitsAuthor(author);
		}
    	
    }
    
    
    //Calcular la cantidad de tweets de un genero 
    @GetMapping("/hitsGenre")
    public Integer hitsGenre(Genre genre) {
    	
    	//Autores segun el genero
    	List <Author> listaAutores=new ArrayList<Author>();
    	authorService.findAuthorFromGenre(genre.getId());
    	
    	Integer counter=0;
    	for (Author author : listaAutores) {
    		counter=counter+hitsAuthor(author);
    	
		}
    	
    	//Se actualiza en genero
    	Genre updatedGenre=new Genre();
    	updatedGenre.setId(genre.getId());
    	updatedGenre.setName(genre.getName());
    	updatedGenre.setHits(counter);
    	return counter;
    }
    
    
    //Actualizar todos los Generos
    @GetMapping("/updateGenre")
    public void updateGenre() {
    	
    	//Todos los generos
    	List <Genre> listaGeneros=new ArrayList<Genre>();
    	listaGeneros=(List<Genre>) genreService.getAllFilms();
    	
    	for (Genre genre : listaGeneros) {
    		hitsGenre(genre);
    	
		}
    	
    }
    	
    //Actualiza todas las entidades
    @GetMapping("/updateAll")
	public void updateAll(){
	  updateBook();
	  updateAuthor();
	  updateGenre();
	}
    
    
    
    //Metodos para crear JSON
    /*public JSONObject top10libros() {
    	
    	JSONObject result = new JSONObject();
        ArrayList<Long> data = new ArrayList<Long>();
        ArrayList<String> label = new ArrayList<String>();
        
        //data.add(searchResponse.getHits().totalHits);
        //label.add(book);
        
        result.put("labels", label);
        result.put("data", data);
        return result;
    }
    */
    
	@GetMapping("/{id}")
	public Map<String, Object> getBookById(@PathVariable String id){
	  return tweetCortoDao.getTweetCortoById(id);
	}
	
	
	
	@PostMapping
	public TweetCorto insertBook(@RequestBody TweetCorto tweet) throws Exception {
	  return tweetCortoDao.insertTweetCorto(tweet);
	}
	
	@PutMapping("/{id}")
	public Map<String, Object> updateBookById(@RequestBody TweetCorto tweet, @PathVariable String id) {
	  return tweetCortoDao.updateTweetCortoById(id, tweet);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable String id) {
		tweetCortoDao.deleteTweetCortoById(id);
	}

}
